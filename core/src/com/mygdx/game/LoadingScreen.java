package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import aurelienribon.tweenengine.equations.Quad;

public class LoadingScreen implements Screen {

    PongForAndroid game;
    Stage stage;
    ProgressBar progressBar;


    public LoadingScreen(PongForAndroid game) {
        this.game = game;
        setupAssetManager();
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();
        BitmapFont loadingFont = getLoadingFont();
        LabelStyle loadingStyle = new LabelStyle(loadingFont, Color.WHITE);
        Label loadLabel = new Label("Loading...", loadingStyle);
        loadLabel.setPosition(200, 300);

        progressBar = new ProgressBar(0, 100, 1, false, skin);
        progressBar.setPosition(200, 200);
        progressBar.setAnimateDuration(1f);
        progressBar.setAnimateInterpolation(Interpolation.sine);


        stage.addActor(loadLabel);
        stage.addActor(progressBar);

    }

    private BitmapFont getLoadingFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                Gdx.files.internal("fonts/LiberationMono-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        BitmapFont titleFont = generator.generateFont(parameter);
        generator.dispose();

        return titleFont;
    }

    private void setupAssetManager() {
        game.assetManager.load("uiskin.atlas", TextureAtlas.class);
        game.assetManager.load("ping.wav", Sound.class);
        game.assetManager.load("recall_of_the_shadows.mp3", Music.class);
        game.assetManager.load("8bit_airship.ogg", Music.class);
        game.titleStyle = new LabelStyle(getTitleFont(), Color.WHITE);
        game.ballImage = makeRectImage(12, 12, Color.WHITE);
        game.smallParticleImage = makeRectImage(2, 2, Color.YELLOW);
        game.mediumParticleImage = makeRectImage(3, 3, Color.YELLOW);
        game.largeParticleImage = makeRectImage(4, 4, Color.ORANGE);
        game.netImage = makeRectImage(2, 2, Color.WHITE);
        game.scoreFont = makeScoreFont();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.075f, 0.059f, 0.188f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.assetManager.update();
        float progress = game.assetManager.getProgress() * 100;
        progressBar.setValue(progress);
        stage.act();
        stage.draw();
        doneLoadingCheck();
    }

    private void doneLoadingCheck() {
        if (game.assetManager.update()) {
            if (progressBar.getValue() == progressBar.getVisualValue()) {
                dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private BitmapFont getTitleFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                Gdx.files.internal("fonts/LiberationMono-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        BitmapFont titleFont = generator.generateFont(parameter);
        generator.dispose();

        return titleFont;
    }

    private Texture makeRectImage(int width, int height, Color color) {
        Pixmap ballPixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        ballPixmap.setColor(color);
        ballPixmap.fill();
        return new Texture(ballPixmap);
    }

    private BitmapFont makeScoreFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                Gdx.files.internal("fonts/LiberationMono-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 55;
        BitmapFont titleFont = generator.generateFont(parameter);
        generator.dispose();

        return titleFont;
    }
}
