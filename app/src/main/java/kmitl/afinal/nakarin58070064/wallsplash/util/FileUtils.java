package kmitl.afinal.nakarin58070064.wallsplash.util;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {

    public static File saveBitmap(File directory, String fileName, Bitmap bitmap,
                                  Bitmap.CompressFormat format, int quality) throws IOException {

        FileOutputStream stream = new FileOutputStream(directory +
                File.separator + fileName);
        bitmap.compress(format, quality, stream);
        stream.close();

        return new File(directory, fileName);
    }

}