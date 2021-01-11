package info.ankurpandya.filepicker;

import android.net.Uri;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * Create by Ankur @ Worktable.sg
 */
public class FilePicker implements DefaultLifecycleObserver {
    private final ActivityResultRegistry mRegistry;
    private ActivityResultLauncher<String> mGetContent;
    private Builder builder;
    //private ActivityResultLauncher<Intent> mStartForResult;

    public FilePicker(@NonNull ActivityResultRegistry registry) {
        mRegistry = registry;
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        mGetContent = mRegistry.register("a", owner, new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        handleActivityResult(uri);
                    }
                });
    }

    private void handleActivityResult(Uri uri) {
        if (builder.filePickerResult != null) {
            builder.filePickerResult.onFilePicked(uri);
        }
    }

    public void pick() {
        mGetContent.launch("image/*");
    }

    public static class Builder {

        private final AppCompatActivity activity;
        private FilePickerResult filePickerResult;

        public Builder(AppCompatActivity activity) {
            this.activity = activity;
        }

        public Builder setFilePickerResult(FilePickerResult filePickerResult) {
            this.filePickerResult = filePickerResult;
            return this;
        }

        public FilePicker build() {
            FilePicker filePicker = new FilePicker(activity.getActivityResultRegistry());
            filePicker.builder = this;
            activity.getLifecycle().addObserver(filePicker);
            return filePicker;
        }
    }

    public interface FilePickerResult {
        void onFilePicked(Uri uri);
    }
}