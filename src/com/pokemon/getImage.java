package com.pokemon;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.*;
import java.nio.channels.*;

public class getImage {
    public String getImage(String pokeName) {
        BufferedImage pokemonImage;
        pokeName = pokeName.toLowerCase();

        try {
            pokemonImage = ImageIO.read(new File("src/com/pokemon/images/" + pokeName + ".png"));
        } catch (IOException e) {
            try {
                URL website = new URL("https://img.pokemondb.net/sprites/red-blue/normal/bulbasaur.png");
                ReadableByteChannel rbc = Channels.newChannel(website.openStream());
                FileOutputStream fos = new FileOutputStream("src/com/pokemon/images/"+pokeName+".png");
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            } catch (Exception e){
                System.out.println("No image available for" + pokeName);
            }
        }
    }
}
