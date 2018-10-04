package atproj.cyplay.com.asperteamapi.domain.interactor;

import android.os.Handler;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import atproj.cyplay.com.asperteamapi.domain.interactor.callback.ResourceRequestCallback;
import atproj.cyplay.com.asperteamapi.domain.repository.AsperTeamApi;
import atproj.cyplay.com.asperteamapi.model.AvgStress;
import atproj.cyplay.com.asperteamapi.model.Stress;
import atproj.cyplay.com.asperteamapi.model.exception.BaseException;
import atproj.cyplay.com.asperteamapi.model.exception.ExceptionType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by andre on 11-Apr-18.
 */

public class StressInteractorOfflineImpl implements StressInteractor {

    AsperTeamApi _asperTeamApi;

    public StressInteractorOfflineImpl(AsperTeamApi asperTeamApi) {
        _asperTeamApi = asperTeamApi;
    }

    public void addRmssd(String userId, int level, int rri, final ResourceRequestCallback<Stress> callback) {
        final ResourceRequestCallback<Stress> addStressCallback = callback;

        Stress stress = new Stress(userId, level, rri);

        try {
            Call<Stress> addStressCall = _asperTeamApi.addStress(stress);
            addStressCall.enqueue(new Callback<Stress>() {
                @Override
                public void onResponse(Call<Stress> call, Response<Stress> response) {
                    if (addStressCallback != null) {
                        if (response.body() != null)
                            addStressCallback.onSucess(response.body());
                        else
                            addStressCallback.onError(new BaseException(ExceptionType.AUTHENTICATION));
                    }
                }

                @Override
                public void onFailure(Call<Stress> call, Throwable t) {
                    addStressCallback.onError(new BaseException(ExceptionType.TECHNICAL));
                }
            });

        } catch (Exception e) {
            addStressCallback.onError(new BaseException(ExceptionType.TECHNICAL));
        }
    }

    public void addStress(String userId, int level, int rri, double lat, double lng, final ResourceRequestCallback<Stress> callback) {
        final ResourceRequestCallback<Stress> addStressCallback = callback;

        Stress stress = new Stress(userId, level, rri, lat, lng);

        try {
            Call<Stress> addStressCall = _asperTeamApi.addStress(stress);
            addStressCall.enqueue(new Callback<Stress>() {
                @Override
                public void onResponse(Call<Stress> call, Response<Stress> response) {
                    if (addStressCallback != null) {
                        if (response.body() != null)
                            addStressCallback.onSucess(response.body());
                        else
                            addStressCallback.onError(new BaseException(ExceptionType.AUTHENTICATION));
                    }
                }

                @Override
                public void onFailure(Call<Stress> call, Throwable t) {
                    addStressCallback.onError(new BaseException(ExceptionType.TECHNICAL));
                }
            });

        } catch (Exception e) {
            addStressCallback.onError(new BaseException(ExceptionType.TECHNICAL));
        }
    }


    public void getStress(String userId, String startDate, String endDate, final ResourceRequestCallback<List<Stress>> callback) {
        final ResourceRequestCallback<List<Stress>> fCallback = callback;

        final List<Stress> stresses = new ArrayList<>();
        stresses.add(new Stress(userId, 300, 20, 48.858798, 2.297023));
        stresses.add(new Stress(userId, 300, 20, 48.857951, 2.295381));
        stresses.add(new Stress(userId, 300, 20, 48.855643, 2.295917));


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fCallback.onSucess(stresses);
            }
        }, 300);
    }

    public void getMonthStress(String userId, String dateTime, final ResourceRequestCallback<List<Stress>> callback) {
        final ResourceRequestCallback<List<Stress>> fCallback = callback;

        final List<Stress> stresses = new ArrayList<>();
        stresses.add(new Stress(userId, 300, 20, 48.858798, 2.297023));
        stresses.add(new Stress(userId, 300, 20, 48.857951, 2.295381));
        stresses.add(new Stress(userId, 300, 20, 48.855643, 2.295917));


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fCallback.onSucess(stresses);
            }
        }, 300);
    }

    public void getAvgRmmssd(String userId, String startTime, String endTime, final ResourceRequestCallback<List<AvgStress>> callback) {
        final ResourceRequestCallback<List<AvgStress>> fCallback = callback;

        try {
            Call<List<AvgStress>> call = _asperTeamApi.getAvgStress(userId, startTime, endTime);
            call.enqueue(new Callback<List<AvgStress>>() {
                @Override
                public void onResponse(Call<List<AvgStress>> call, Response<List<AvgStress>> response) {
                    if (fCallback != null) {
                        if (response.body() != null)
                            fCallback.onSucess(response.body());
                        else
                            fCallback.onError(new BaseException(ExceptionType.AUTHENTICATION));
                    }
                }

                @Override
                public void onFailure(Call<List<AvgStress>> call, Throwable t) {
                    fCallback.onError(new BaseException(ExceptionType.TECHNICAL));
                }
            });

        } catch (Exception e) {
            fCallback.onError(new BaseException(ExceptionType.TECHNICAL));
        }
    }

    public void getDayAvgRmmssd(String userId, String dateTime, final ResourceRequestCallback<List<AvgStress>> callback) {
        final ResourceRequestCallback<List<AvgStress>> fCallback = callback;

        String time_format_pattern = "yyyy-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(time_format_pattern);
        Calendar date = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();
        try {
            date.setTime(format.parse(dateTime));
            maxDate.setTime(format.parse(dateTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        maxDate.add(Calendar.DAY_OF_MONTH, 1);

        final List<AvgStress> avgStress = new ArrayList<>();

        for (int i = 8; i < 20; i++) {
            Random r = new Random();
            int level = r.nextInt(90 - 50) + 50;
            avgStress.add(new AvgStress((double)level, i));
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fCallback.onSucess(avgStress);
            }
        }, 300);
    }

    public void getMonthAvgRmmssd(String userId, String dateTime, final ResourceRequestCallback<List<AvgStress>> callback) {
        final ResourceRequestCallback<List<AvgStress>> fCallback = callback;

        String time_format_pattern = "yyyy-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(time_format_pattern);
        Calendar date = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();
        try {
            date.setTime(format.parse(dateTime));
            maxDate.setTime(format.parse(dateTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int maxDay = date.getActualMaximum(Calendar.DAY_OF_MONTH);
        maxDate.set(Calendar.DAY_OF_MONTH, maxDay);

        final List<AvgStress> avgStress = new ArrayList<>();


        for (int i = 1; i <= maxDay; i++) {
            Random r = new Random();
            int level = r.nextInt(90 - 50) + 50;
            DecimalFormat dayFormat = new DecimalFormat("00");
            String dtStr = dateTime.substring(0, 8) + dayFormat.format(i);
            avgStress.add(new AvgStress((double)level, dtStr));
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fCallback.onSucess(avgStress);
            }
        }, 300);
    }
}
