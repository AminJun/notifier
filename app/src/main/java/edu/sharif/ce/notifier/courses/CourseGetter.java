package edu.sharif.ce.notifier.courses;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by jun on 8/23/16.
 * You can't understand this, Unless you're a genius!
 */
public class CourseGetter implements Callback<List<Course>> {
    private final static String URL = "https://notifier.codelopers.com/";
    private final CoursesAPI callInstance;
    private final DataSetUpdatable<List<Course>> dataNeededObject;

    public CourseGetter(DataSetUpdatable<List<Course>> dataNeededObject) {
        this.dataNeededObject = dataNeededObject;
        this.callInstance = new Retrofit.Builder().baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create()).build().create(CoursesAPI.class);
    }

    public void download() {
        callInstance.getCourses().enqueue(this);
    }


    @Override
    public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
        dataNeededObject.updateDataSet(response.body());
    }

    @Override
    public void onFailure(Call<List<Course>> call, Throwable t) {
        download();
    }

    public interface CoursesAPI {
        @GET("api/courses")
        Call<List<Course>> getCourses();
    }
}
