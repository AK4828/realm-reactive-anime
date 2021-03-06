package learn.akm.realm.application;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import learn.akm.realm.config.Migrations;

/**
 * Created by AKM on 1/15/18.
 */

public class RealmApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("myanime.realm")
                .schemaVersion(0)
                .migration(new Migrations())
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(configuration);

    }
}
