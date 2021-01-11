package info.ankurpandya.androidfilepicker;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;

import info.ankurpandya.filepicker.FilePicker;

public class MainActivity extends AppCompatActivity implements FilePicker.FilePickerResult {

    FilePicker filePicker;
    TextView text;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.text);
        image = findViewById(R.id.image);

        filePicker = new FilePicker.Builder(this)
                .setFilePickerResult(this)
                .build();

        text.setOnClickListener(view -> filePicker.pick());
    }

    @Override
    public void onFilePicked(Uri uri) {
        InputStream inputStream = null;
        try {
            inputStream = getApplicationContext().getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            image.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}