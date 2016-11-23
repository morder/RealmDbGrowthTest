package com.example.yanis.realmtestapp;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.math.BigInteger;
import java.util.Random;
import java.util.UUID;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    protected Logger LOGGER = Logger.getLogger(MainActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.deleteRealm(RealmCreator.getRealmConfig(RealmCreator.Db.Test));
        Realm realm = RealmCreator.getRealmInstance(RealmCreator.Db.Test);

        /*Worker worker = new Worker("worker1");
        worker.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        for (int i = 0; i < 1000; i++) {
            realm.beginTransaction();
            TestEvent event = createTestEvent();
            realm.copyToRealmOrUpdate(event);
            realm.commitTransaction();

            LOGGER.showDbStats("iteration " + i);
        }
        realm.close();

        LOGGER.showDbStats();

        Realm.compactRealm(RealmCreator.getRealmConfig(RealmCreator.Db.Test));
        LOGGER.showDbStats();
    }

    private class Worker extends Thread {
        public Handler mHandler;

        public Worker(String name) {
            super(name);
        }

        @Override
        public void run() {
            Looper.prepare();

            mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {

                    Realm realm = RealmCreator.getRealmInstance(RealmCreator.Db.Test);
                    realm.beginTransaction();

                    TestEvent event = createTestEvent();
                    realm.copyToRealmOrUpdate(event);

                    realm.commitTransaction();
                    realm.close();

                    LOGGER.showDbStats();
                }
            };

            Looper.loop();
        }
    }

    private TestEvent createTestEvent() {
        Random random = new Random();
        final TestEvent row = new TestEvent();
        row.setObjId(UUID.randomUUID().toString());
        row.setField1(random.nextBoolean());
        row.setField2(new BigInteger(130, random).toString(32));
        row.setField3(random.nextInt());
        row.setField4(new BigInteger(130, random).toString(32));
        row.setField5(random.nextLong());
        return row;
    }

}
