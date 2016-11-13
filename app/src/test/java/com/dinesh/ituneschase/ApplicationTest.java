package com.dinesh.ituneschase;

import android.app.Application;
import android.provider.Settings;
import android.test.ApplicationTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;

/**
 * Created by Dinesh Reddy Pogula on 11/12/16.
 */

public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testSplit(){
        System.out.println("getSpecialString".replaceAll("([A-Z])", " $1").toLowerCase());
    }

    public void testListEnum(){
        ArrayList<Entity> all = new ArrayList<>(EnumSet.allOf(Entity.class));
        System.out.println(Arrays.toString(all.toArray()));
    }

    public void testEnumString(){
        Entity e = Entity.movie;
        assertEquals("check the enum toString ","movie",e.toDisplayString());
         e = Entity.allArtist;
        assertEquals("check the enum toString ","all artist",e.toDisplayString());
    }
}
