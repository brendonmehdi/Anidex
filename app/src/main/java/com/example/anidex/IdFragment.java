package com.example.anidex;

import android.net.Uri;
import android.util.Base64;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IdFragment extends Fragment {

    private ImageView imageView;
    private ActivityResultLauncher<String> getContent;
    private OpenAIApiService apiService;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getContent = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
            if (uri != null) {
                imageView.setImageURI(uri);
                String imageBase64 = convertImageToBase64(uri);
                // Optionally, call uploadImage here if you want to auto-upload after selection
                uploadImage(imageBase64);
                // uploadImage(imageBase64);
            }
        });
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request originalRequest = chain.request();
                    Request.Builder builder = originalRequest.newBuilder()
                            .header("Authorization", "Bearer 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");
                    Request newRequest = builder.build();
                    return chain.proceed(newRequest);
                })
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openai.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();



        apiService = retrofit.create(OpenAIApiService.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_id, container, false);
        imageView = view.findViewById(R.id.imageView);
        // Find the ImageView by its ID
        imageView = view.findViewById(R.id.imageView);

        // Find the Button by its ID and set an OnClickListener
        Button buttonUpload = view.findViewById(R.id.buttonUpload);
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the content picker when the button is clicked
                getContent.launch("image/*");
            }
        });
        return view;
    }

    public String convertImageToBase64(Uri imageUri) {
        try {
            InputStream inputStream = getContext().getContentResolver().openInputStream(imageUri);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            byte[] imageBytes = outputStream.toByteArray();
            return Base64.encodeToString(imageBytes, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void uploadImage(String base64Image) {
        // Constructing the JSON payload
        JSONObject payload = new JSONObject();
        try {
            payload.put("model", "gpt-4-vision-preview");
            JSONArray messages = new JSONArray();
            JSONObject message = new JSONObject();
            message.put("role", "user");
            JSONArray content = new JSONArray();

            JSONObject textContent = new JSONObject();
            textContent.put("type", "text");
            textContent.put("text", "which anime character is this?");
            content.put(textContent);

            JSONObject imageContent = new JSONObject();
            imageContent.put("type", "image_url");
            JSONObject imageUrl = new JSONObject();
            imageUrl.put("url", "data:image/jpeg;base64," + base64Image); // Ensure base64Image is correctly encoded
            imageContent.put("image_url", imageUrl);
            content.put(imageContent);

            message.put("content", content);
            messages.put(message);
            payload.put("messages", messages);
            payload.put("max_tokens", 300);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), payload.toString());

        Call<ApiResponse> call = apiService.uploadImage(requestBody);

        call.enqueue(new retrofit2.Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    if (!apiResponse.getChoices().isEmpty()) {
                        String responseText = apiResponse.getChoices().get(0).getMessage().getContent();
                        getActivity().runOnUiThread(() ->
                                Toast.makeText(getContext(), "Response: " + responseText, Toast.LENGTH_LONG).show());
                    } else {
                        getActivity().runOnUiThread(() ->
                                Toast.makeText(getContext(), "Upload successful but no response content.", Toast.LENGTH_LONG).show());
                    }
                } else {

                    // Log error response
                    String errorBody = null;
                    try {
                        Log.e("APIError", "Error response: " + response.errorBody().string());
                        errorBody = response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.e("UploadError", "Upload failed: " + errorBody);
                    String finalErrorBody = errorBody;
                    getActivity().runOnUiThread(() ->
                            Toast.makeText(getContext(), "Upload failed: " + response.code() + " - " + finalErrorBody, Toast.LENGTH_LONG).show());
                    System.out.println(finalErrorBody);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                getActivity().runOnUiThread(() ->
                        Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show());
            }


        });
    }




}
