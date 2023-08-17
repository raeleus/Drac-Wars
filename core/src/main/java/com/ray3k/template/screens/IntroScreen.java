package com.ray3k.template.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.github.tommyettinger.textra.TypingAdapter;
import com.github.tommyettinger.textra.TypingConfig;
import com.github.tommyettinger.textra.TypingLabel;
import com.github.tommyettinger.textra.TypingListener;
import com.ray3k.template.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.*;

public class IntroScreen extends JamScreen {
    private Stage stage;
    private final static Color BG_COLOR = new Color(Color.BLACK);
    private int step;
    private Music speech;
    private SpineDrawable spineDrawable;
    
    @Override
    public void show() {
        super.show();
        
        speech = bgm_intro;
        speech.play();
        
        stage = new Stage(new FitViewport(1024, 576), batch);
        Gdx.input.setInputProcessor(stage);
    
        var root = new Stack();
        root.setFillParent(true);
        root.setTouchable(Touchable.enabled);
        stage.addActor(root);
        
        var table = new Table();
        root.add(table);
        
        table.top().right().pad(30);
        spineDrawable = new SpineDrawable(skeletonRenderer, SpineIntro.skeletonData, SpineIntro.animationData);
        spineDrawable.getAnimationState().setAnimation(0, SpineIntro.animationStand, true);
        spineDrawable.setCrop(0, 0, 240, 200);
        var image = new Image(spineDrawable);
        table.add(image);
        
        table = new Table();
        root.add(table);
        
        table.bottom();
        var letter = new TypingLabel(Gdx.files.internal("intro.txt").readString(), skin, "small");
        letter.setWrap(true);
        table.add(letter).growX().pad(18).padBottom(0);
        
        table.row();
        var closing = new TypingLabel("Your true love,\nJohnathan", skin, "small");
        closing.setAlignment(Align.right);
        closing.pause();
        table.add(closing).pad(20).padTop(50).expandX().right();
        
        letter.setTypingListener(new TypingAdapter() {
            @Override
            public void end() {
                closing.restart();
                step++;
            }
        });
        
        table.row();
        var textButton = new TextButton("<< CLICK TO CONTINUE >>", skin);
        table.add(textButton);
        
        root.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (step == 0) {
                    letter.setTypingListener(null);
                    letter.skipToTheEnd(true);
                    closing.skipToTheEnd(true);
                    step++;
                } else {
                    speech.stop();
                    Gdx.input.setInputProcessor(null);
                    Core.core.transition(new GameScreen());
                    bgm_menuIntro.stop();
                    bgm_menu.stop();
                    bgm_intro.stop();
                }
            }
        });
    }
    
    @Override
    public void act(float delta) {
        if (!bgm_menuIntro.isPlaying() && !bgm_menu.isPlaying()) {
            bgm_menu.play();
            bgm_menu.setVolume(core.bgm);
            bgm_menu.setLooping(true);
        }
        stage.act(delta);
    }
    
    @Override
    public void draw(float delta) {
        Gdx.gl.glClearColor(BG_COLOR.r, BG_COLOR.g, BG_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        stage.draw();
    }
    
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    
    @Override
    public void pause() {
    
    }
    
    @Override
    public void resume() {
    
    }
    
    @Override
    public void dispose() {
    
    }
}
