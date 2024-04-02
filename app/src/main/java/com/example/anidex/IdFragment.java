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
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;


import com.example.anidex.OpenAI.ApiResponse;
import com.example.anidex.OpenAI.OpenAIApiService;

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


// Import statements...

/**
 * Fragment responsible for handling image selection and uploading for analysis.
 */
public class IdFragment extends Fragment {
    // Class-level variables for UI components and API service.
    private ImageView imageView; // Displays selected image.
    private TextView imgText; // Displays analysis result.
    private ActivityResultLauncher<String> getContent; // Handles result of image selection.
    private OpenAIApiService apiService; // API service for uploading images.

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initializes the content launcher with a callback for image selection.
        getContent = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
            if (uri != null) {
                imageView.setImageURI(uri); // Displays the selected image.
                String imageBase64 = convertImageToBase64(uri); // Converts image to Base64.
                uploadImage(imageBase64); // Uploads image for analysis. and gives back and sets our response
            }
        });


//        OkHttpClient can be customized with interceptors, which allow you to add, remove,
//        or transform requests and responses. For instance, you can use an interceptor to
//        add headers to every request, log requests and responses, or handle caching



        // Configures the OkHttpClient with an Authorization header for API requests.
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request originalRequest = chain.request();
                    Request.Builder builder = originalRequest.newBuilder()
                            .header("Authorization", "Bearer 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");
                    Request newRequest = builder.build();
                    return chain.proceed(newRequest);
                })
                .build();



//        Retrofit is a type-safe HTTP client for Android and Java, also developed by
//        Square. It turns your HTTP API into a Java interface. Retrofit uses annotations to
//        encode details about API requests and parameters, making it easy to connect to a web service with minimal boilerplate code


//        Retrofit can automatically serialize API responses into Java objects using converters like Gson, Moshi, or Jackson.
//        This serialization process simplifies handling JSON or XML data, allowing you to work directly with
//        Java objects instead of parsing raw response data manually.


        // Sets up Retrofit with the base URL and JSON converter.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openai.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        // Creates an instance of the API service.
        apiService = retrofit.create(OpenAIApiService.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_id, container, false);

//        imageView = view.findViewById(R.id.imageAnime);
//        // Find the ImageView by its ID
//        imageView = view.findViewById(R.id.imageAnime);

        // Find the Button by its ID and set an OnClickListener
        Button buttonUpload = view.findViewById(R.id.buttonUpload);
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the content picker when the button is clicked
                getContent.launch("image/*");
            }
        });


        // Initializes UI components.
        imageView = view.findViewById(R.id.imageView);
        imgText = view.findViewById(R.id.imageText);
//        Button buttonUpload = view.findViewById(R.id.buttonUpload);

        // Sets a click listener on the upload button to launch the image picker.
        buttonUpload.setOnClickListener(v -> getContent.launch("image/*"));

        return view;
    }

    /**
     * Converts the selected image URI to a Base64-encoded string.
     * @param imageUri The URI of the selected image.
     * @return A Base64-encoded string representation of the image.
     */
    public String convertImageToBase64(Uri imageUri) {
        try {
//            This line opens an InputStream for the content URI of the image. A content URI is a pointer to data within a content provider, and this line reads the image data into the app
            InputStream inputStream = getContext().getContentResolver().openInputStream(imageUri);

//            A ByteArrayOutputStream is initialized. This stream will be used to write the bytes read from the input stream (the image data).
//            The advantage of using ByteArrayOutputStream is that it grows automatically as data is written to it.
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();


            //The while loop reads data from the input stream into a buffer array (byte[] buffer = new byte[1024];)
            // and then writes it to the outputStream. The buffer size is set to 1024 bytes, meaning it reads up to 1024 bytes at a time.
//            bytesRead holds the number of bytes actually read, and -1 indicates the end of the stream (no more data to read).
//            outputStream.write(buffer, 0, bytesRead); writes the bytes from buffer into the outputStream.
//            It writes from the start (0) to bytesRead (the end of the data read).
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

//            After reading all the data from the input stream and writing it to the outputStream,
//            toByteArray() converts the accumulated bytes into a byte array (imageBytes).
            byte[] imageBytes = outputStream.toByteArray();


//            The byte array is then encoded into a Base64 string. Base64 is a way of encoding binary data using only printable (text) characters.
//            It's commonly used to encode binary files like images for embedding in text formats like JSON or XML.
            return Base64.encodeToString(imageBytes, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Uploads the Base64-encoded image to OpenAI for analysis.
     * @param base64Image The Base64-encoded image string.
     */
    private void uploadImage(String base64Image) {
        // constructs the JSON payload for the API request
        JSONObject payload = new JSONObject();
        try {
            //sets the model
            payload.put("model", "gpt-4-vision-preview");
            //created messages jsonarray
            JSONArray messages = new JSONArray();
            //created message json object
            JSONObject message = new JSONObject();
            //sets the role
            message.put("role", "user");
            //created jsonArray for content
            JSONArray content = new JSONArray();

            //json object fot the text aka the prompt
            JSONObject textContent = new JSONObject();

            //add the content type and prompt to the above object
            textContent.put("type", "text");
            textContent.put("text", "which anime character is this? give the feed back as an overly excited anime fan, only give back 2-4 sentences");
            //puts the text content in our array of content json array
            content.put(textContent);

            //created json object for the base 64 image
            JSONObject imageContent = new JSONObject();
            //puting in the image url
            imageContent.put("type", "image_url");
            //created image url object
            JSONObject imageUrl = new JSONObject();
            //puting my bas65Image
            imageUrl.put("url", "data:image/jpeg;base64," + base64Image);
            //puuting in the image url
            imageContent.put("image_url", imageUrl);
            //put the image content inside of our content array
            content.put(imageContent);

            //puts out one message into the messages array
            message.put("content", content);
            messages.put(message);
            //puts our messages into the payload
            payload.put("messages", messages);
            //sets max tokens
            payload.put("max_tokens", 300);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Creates a RequestBody from the JSON payload and makes the API call.
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), payload.toString());

        Call<ApiResponse> call = apiService.uploadImage(requestBody);

        // Handles the API response.
        call.enqueue(new retrofit2.Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    if (!apiResponse.getChoices().isEmpty()) {
                        // Extracts and displays the response text.
                        String responseText = apiResponse.getChoices().get(0).getMessage().getContent();
                        getActivity().runOnUiThread(() -> {
                            Toast.makeText(getContext(), "Success", Toast.LENGTH_LONG).show();
                            imgText.setText(responseText); // Updates the TextView with analysis result.
                        });
                    } else {
                        getActivity().runOnUiThread(() ->
                                Toast.makeText(getContext(), "Upload successful but no response content.", Toast.LENGTH_LONG).show());
                    }
                } else {
                    // Logs and displays error responses.
                    String errorBody = null;
                    try {
                        errorBody = response.errorBody().string();
                        Log.e("APIError", "Error response: " + errorBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    final String finalErrorBody = errorBody;
                    getActivity().runOnUiThread(() ->
                            Toast.makeText(getContext(), "Upload failed: " + response.code() + " - " + finalErrorBody, Toast.LENGTH_LONG).show());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                // Handles request failures.
                getActivity().runOnUiThread(() ->
                        Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show());
            }
        });
    }
}
