package atproj.cyplay.com.asperteamapi.domain.interactor;

import android.os.Handler;

import atproj.cyplay.com.asperteamapi.domain.interactor.callback.ResourceRequestCallback;
import atproj.cyplay.com.asperteamapi.domain.repository.AsperTeamApi;
import atproj.cyplay.com.asperteamapi.model.Advice;
import atproj.cyplay.com.asperteamapi.model.Situation;
import atproj.cyplay.com.asperteamapi.model.SituationResource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andre on 11-Apr-18.
 */

public class SituationInteractorOfflineImpl implements SituationInteractor {

    AsperTeamApi _asperTeamApi;

    public SituationInteractorOfflineImpl(AsperTeamApi asperTeamApi) {
        _asperTeamApi = asperTeamApi;
    }

    public void getSituations(String type, final ResourceRequestCallback<List<Situation>> callback) {
        final ResourceRequestCallback<List<Situation>> getSituationsCallback = callback;

        final List<Situation> situations = new ArrayList<>();
        situations.add(new Situation(1, "Quae est veritatis. Recusandae aperiam magnam pariat"));
        situations.add(new Situation(2, "Ut odio voluptas ab"));


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getSituationsCallback.onSucess(situations);
            }
        }, 300);

    }

    public void getSituationResources(int situationId, final ResourceRequestCallback<List<SituationResource>> callback) {
        final ResourceRequestCallback<List<SituationResource>> getSituationResourcesCallback = callback;

        final List<SituationResource> resources = new ArrayList<>();
        resources.add(new SituationResource(
                "http://iytontg.ur",
                "Itaque explicabo nihil magnam iusto laudantium esse incidunt recusandae totam verita",
                situationId));
        resources.add(new SituationResource(
                "http://acijcutyxl.sd",
                "Possimus velit harum alias expedita cum facilis libero",
                situationId));
        resources.add(new SituationResource(
                "http://gcizxh.ynn",
                "Sint sed maiores deleniti consequuntur",
                situationId));


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getSituationResourcesCallback.onSucess(resources);
            }
        }, 300);

    }

    public void getAdvices(String dateTime, final ResourceRequestCallback<List<Advice>> callback) {
        final ResourceRequestCallback<List<Advice>> getAdviceCallback = callback;

        final List<Advice> advices = new ArrayList<>();

        advices.add(new Advice(
                "J’ai repéré que l’après midi, il y a beaucoup de bruit sur l’open space et cela me fatigue voire me stresse. Mon astuce, je porte un casque anti bruit ou je me mets de la musique douce pendant 2 heures",
                "561c9e74-fef3-4d76-b168-917a9e42f012"));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getAdviceCallback.onSucess(advices);
            }
        }, 300);
    }
}
