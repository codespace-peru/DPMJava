package com.cmontero.tallerdpmjava.camara;

import static android.app.Activity.RESULT_OK;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cmontero.tallerdpmjava.databinding.FragmentCamaraBinding;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class CamaraFragment extends Fragment {

    Bitmap bitmap;
    File photoFile;
    //String prefix = ;
    String nombreImagen;
    ActivityResultLauncher<Intent> resultLauncher;
    String url = "http://cmontero.atwebpages.com/index.php/upload";
    RequestQueue cola;
    private FragmentCamaraBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCamaraBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imgPreview = binding.imgPreview;
        ImageButton imgCamara = binding.imgCamara;
        //Button btnGuardarGaleria = binding.btnGuardarGaleria;
        Button btnGuardarNube = binding.btnGuardarNube;

        cola = Volley.newRequestQueue(requireContext());

        // Camara:
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                        imgPreview.setImageBitmap(bitmap);
                        btnGuardarNube.setEnabled(true);
                        //btnGuardarGaleria.setEnabled(true);
                    }
                });

        imgCamara.setOnClickListener(v -> openCamara());

        btnGuardarNube.setOnClickListener(v -> {
            if (bitmap != null) {
                uploadImage();
            }
        });

        /*btnGuardarGaleria.setOnClickListener(v -> {
            if (bitmap != null) {
                saveImage();
            }
        });*/

    }

    private String generarNombreFile(long currentMillis){
        nombreImagen = "imagen_" + currentMillis;
        return nombreImagen;
    }

    private void saveImage() {
        OutputStream fos = null;
        // Funciona para Android Q en adelante
        ContentResolver resolver = requireActivity().getContentResolver();
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, nombreImagen);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MyApp");
        values.put(MediaStore.Images.Media.IS_PENDING,1);

        Uri collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        Uri imageUri = resolver.insert(collection, values);

        try {
            fos = resolver.openOutputStream(imageUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        values.clear();
        values.put(MediaStore.Images.Media.IS_PENDING,0);
        resolver.update(imageUri, values, null, null);


        boolean saved = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        if(saved){
            Toast.makeText(requireContext(),"La imagen se guardó",Toast.LENGTH_SHORT).show();
        }

        if(fos != null){
            try {
                fos.flush();
                fos.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    private void uploadImage() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Toast.makeText(requireContext(),jsonObject.getString("mensaje"),Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                },
                error -> {Toast.makeText(requireContext(),error.toString(),Toast.LENGTH_SHORT).show();})
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("image", imageToString(bitmap));
                params.put("name", nombreImagen + ".jpg");
                return params;
            }

        };

        cola.add(stringRequest);
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private void openCamara() {
        try{
            File storageDirectory = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            long currentMillis = System.currentTimeMillis() /1000;
            String nombreImagen = generarNombreFile(currentMillis);
            photoFile = File.createTempFile(nombreImagen, ".jpg", storageDirectory);
            if(photoFile.exists()){
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(cameraIntent.resolveActivity(requireActivity().getPackageManager())!= null){
                    Uri imageUri = FileProvider.getUriForFile(requireContext(), requireActivity().getPackageName() + ".provider", photoFile);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    resultLauncher.launch(cameraIntent);
                }
                else
                    Toast.makeText(requireContext(),"No se puede abrir la camara", Toast.LENGTH_SHORT).show();
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}