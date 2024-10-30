package util;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.observables.GroupedObservable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import model.Photo;
import model.PhotoSize;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class PhotoProcessor {

    private static final Logger log = Logger.getLogger(PhotoProcessor.class.getName());
    private final ObservableTransformer<Photo, Photo> processingTransformer;


    public PhotoProcessor() {
        this.processingTransformer = createProcessingPhotoTransformer();
    }

    public ObservableTransformer<Photo, Photo> getProcessingTransformer() {
        return processingTransformer;
    }

    private Boolean isPhotoValid(Photo photo) {
        return PhotoSize.resolve(photo) != PhotoSize.SMALL;
    }

    private Observable<Photo> handleLargePhoto(GroupedObservable<PhotoSize, Photo> group) {
        return group
                .observeOn(Schedulers.computation())
                .map(this::convertToMiniature);
    }

    private Observable<Photo> handleMediumPhoto(GroupedObservable<PhotoSize, Photo> group) {
        return group
                .observeOn(Schedulers.io())
                .buffer(5, TimeUnit.SECONDS)
                .flatMap(Observable::fromIterable);
    }

    private ObservableTransformer<Photo, Photo> createProcessingPhotoTransformer() {
        return observable ->
                observable
                        .filter(this::isPhotoValid)
                        .groupBy(PhotoSize::resolve)
                        .flatMap(group -> switch (Objects.requireNonNull(group.getKey())) {
                            case LARGE -> handleLargePhoto(group);
                            case MEDIUM -> handleMediumPhoto(group);
                            default -> group;
                        });
    }

    public Photo convertToMiniature(Photo photo) throws IOException {
        log.info("...Converting photo... : " + photo.getPhotoData().length);
        return resize(photo, 300, 200);
    }


    private Photo resize(Photo photo, int scaledWidth, int scaledHeight)
            throws IOException {

        try (InputStream inputStream = new ByteArrayInputStream(photo.getPhotoData())) {
            BufferedImage inputImage = ImageIO.read(inputStream);

            if (inputImage != null) {
                Dimension scaledDimension = rescaleKeepingRatio(inputImage, scaledWidth, scaledHeight);

                BufferedImage outputImage = new BufferedImage(scaledDimension.width,
                        scaledDimension.height, inputImage.getType());

                Graphics2D g2d = outputImage.createGraphics();
                g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
                g2d.dispose();

                try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                    ImageIO.write(outputImage, photo.getExtension(), outputStream);

                    return new Photo(photo.getDownloadedDate(), photo.getExtension(), outputStream.toByteArray());
                }
            }
        } catch (IOException e) {
            log.warning("Miniature could not be created for photo: " + photo.getId());
        }
        return photo;
    }

    private Dimension rescaleKeepingRatio(BufferedImage inputImage, int preferredWidth, int preferredHeight) {
        double widthRatio = inputImage.getWidth() / (double) preferredWidth;
        double heightRatio = inputImage.getHeight() / (double) preferredHeight;

        int finalWidth = preferredWidth;
        int finalHeight = preferredHeight;
        if (widthRatio > heightRatio) {
            finalHeight = (int) (inputImage.getHeight() / heightRatio);
        } else {
            finalWidth = (int) (inputImage.getWidth() / widthRatio);
        }
        return new Dimension(finalWidth, finalHeight);
    }
}
