package me.levondovlatyan.turtleup;

import android.content.Context;
import java.util.Random;
import java.lang.String;
import java.util.stream.*;

public class PlatformSelection {

    private Random random = new Random();

    private int[] platform_probs = {10,0,0};
    private String[] platforms = {"basic","disappear","bounce"};

    private int[] totalcum = new int[platform_probs.length];
    private int total = 0;

    private int X;
    private int Y;
    private Context cont;

    public PlatformSelection(Context context, int screenX, int screenY) {

        cont = context;
        X = screenX;
        Y = screenY;

        // generate totalcum array where each element is the sum of previous elements
        totalcum[0] = platform_probs[0];
        for(int i=0; i<platform_probs.length-1;i++) {
            totalcum[i+1] = platform_probs[i+1] + totalcum[i];
        }
        total = totalcum[totalcum.length-1];
    }

    private String getPlatformName() {
        // return string of the choosen platform
        int val = random.nextInt(total) + 1;

        for(int i=0; i<totalcum.length;i++) {
            if (val <= totalcum[i]) {
                return platforms[i];
            }
        }

        return platforms[0]; // this should never happen
    }

    public Platform getPlatform() {
        String platformName = this.getPlatformName();


        if (platformName.equals("basic")) {
            Platform_Basic platform = new Platform_Basic(cont, X, Y);
            return platform;
        } else {
            // should never happen
            Platform_Basic platform = new Platform_Basic(cont, X, Y);
            return platform;
        }

    }

}