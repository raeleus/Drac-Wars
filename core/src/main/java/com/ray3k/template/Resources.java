package com.ray3k.template;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.esotericsoftware.spine.Animation;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.BoneData;
import com.esotericsoftware.spine.EventData;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SlotData;
import java.lang.String;

public class Resources {
    public static TextureAtlas textures_textures;

    public static Skin skin_skin;

    public static Sound sfx_bloodSplatter;

    public static Sound sfx_click;

    public static Sound sfx_die;

    public static Sound sfx_door;

    public static Sound sfx_drakWars;

    public static Sound sfx_evilLaugh;

    public static Sound sfx_finish;

    public static Sound sfx_grenade;

    public static Sound sfx_gun;

    public static Sound sfx_heal;

    public static Sound sfx_heeHee;

    public static Sound sfx_hmm;

    public static Sound sfx_hurt;

    public static Sound sfx_inventory;

    public static Sound sfx_magic;

    public static Sound sfx_money;

    public static Sound sfx_ohYeah;

    public static Sound sfx_pages;

    public static Sound sfx_portal;

    public static Sound sfx_slash;

    public static Sound sfx_vampAttack;

    public static Sound sfx_vamp;

    public static Sound sfx_whip;

    public static Music bgm_audioSample;

    public static Music bgm_below50Hp;

    public static Music bgm_DobrujaAndCrisana;

    public static Music bgm_hasDebtOrMoneyInBank;

    public static Music bgm_intro;

    public static Music bgm_main;

    public static Music bgm_menuIntro;

    public static Music bgm_menu;

    public static Music bgm_Moldovia;

    public static Music bgm_noMoneyOrItemsOnHand;

    public static Music bgm_ResultsINTRO;

    public static Music bgm_ResultsLOOP;

    public static Music bgm_Transylvania;

    public static Music bgm_underAttack;

    public static Music bgm_WallachiaAndBukovina;

    public static void loadResources(AssetManager assetManager) {
        textures_textures = assetManager.get("textures/textures.atlas");
        SpineBlood.skeletonData = assetManager.get("spine/blood.json");
        SpineBlood.animationData = assetManager.get("spine/blood.json-animation");
        SpineBlood.animationAnimation = SpineBlood.skeletonData.findAnimation("animation");
        SpineBlood.animationStand = SpineBlood.skeletonData.findAnimation("stand");
        SpineBlood.eventSfxBloodSplatter = SpineBlood.skeletonData.findEvent("sfx/blood splatter");
        SpineBlood.eventSfxDrakWars = SpineBlood.skeletonData.findEvent("sfx/drak wars");
        SpineBlood.boneRoot = SpineBlood.skeletonData.findBone("root");
        SpineBlood.boneBloodDrip01 = SpineBlood.skeletonData.findBone("blood-drip-01");
        SpineBlood.boneBloodDrip02 = SpineBlood.skeletonData.findBone("blood-drip-02");
        SpineBlood.slotWhite = SpineBlood.skeletonData.findSlot("white");
        SpineBlood.slotBlood01 = SpineBlood.skeletonData.findSlot("blood-01");
        SpineBlood.slotBlood02 = SpineBlood.skeletonData.findSlot("blood-02");
        SpineBlood.slotBlood03 = SpineBlood.skeletonData.findSlot("blood-03");
        SpineBlood.slotBlood04 = SpineBlood.skeletonData.findSlot("blood-04");
        SpineBlood.slotBlood05 = SpineBlood.skeletonData.findSlot("blood-05");
        SpineBlood.slotBloodDrip01 = SpineBlood.skeletonData.findSlot("blood-drip-01");
        SpineBlood.slotBloodDrip02 = SpineBlood.skeletonData.findSlot("blood-drip-02");
        SpineBlood.skinDefault = SpineBlood.skeletonData.findSkin("default");
        SpineIntro.skeletonData = assetManager.get("spine/intro.json");
        SpineIntro.animationData = assetManager.get("spine/intro.json-animation");
        SpineIntro.animationAnimation = SpineIntro.skeletonData.findAnimation("animation");
        SpineIntro.animationStand = SpineIntro.skeletonData.findAnimation("stand");
        SpineIntro.boneRoot = SpineIntro.skeletonData.findBone("root");
        SpineIntro.slotSpineIntro = SpineIntro.skeletonData.findSlot("spine/intro");
        SpineIntro.skinDefault = SpineIntro.skeletonData.findSkin("default");
        SpineLogo.skeletonData = assetManager.get("spine/logo.json");
        SpineLogo.animationData = assetManager.get("spine/logo.json-animation");
        SpineLogo.animationAnimation = SpineLogo.skeletonData.findAnimation("animation");
        SpineLogo.animationStand = SpineLogo.skeletonData.findAnimation("stand");
        SpineLogo.eventSfxHeeHee = SpineLogo.skeletonData.findEvent("sfx/hee hee");
        SpineLogo.eventSfxHmm = SpineLogo.skeletonData.findEvent("sfx/hmm");
        SpineLogo.eventSfxOhYeah = SpineLogo.skeletonData.findEvent("sfx/oh yeah");
        SpineLogo.boneRoot = SpineLogo.skeletonData.findBone("root");
        SpineLogo.boneLogoRaeleus = SpineLogo.skeletonData.findBone("logo-raeleus");
        SpineLogo.boneSpineZebra = SpineLogo.skeletonData.findBone("spine/zebra");
        SpineLogo.boneSpineShroom = SpineLogo.skeletonData.findBone("spine/shroom");
        SpineLogo.boneSpineBefluffly = SpineLogo.skeletonData.findBone("spine/befluffly");
        SpineLogo.slotWhite = SpineLogo.skeletonData.findSlot("white");
        SpineLogo.slotLogoRaeleus = SpineLogo.skeletonData.findSlot("logo-raeleus");
        SpineLogo.slotSpineZebra = SpineLogo.skeletonData.findSlot("spine/zebra");
        SpineLogo.slotSpineShroom = SpineLogo.skeletonData.findSlot("spine/shroom");
        SpineLogo.slotSpineBefluffly = SpineLogo.skeletonData.findSlot("spine/befluffly");
        SpineLogo.skinDefault = SpineLogo.skeletonData.findSkin("default");
        skin_skin = assetManager.get("skin/skin.json");
        SkinSkinStyles.bDefault = skin_skin.get("default", Button.ButtonStyle.class);
        SkinSkinStyles.lCash = skin_skin.get("cash", Label.LabelStyle.class);
        SkinSkinStyles.lInventory = skin_skin.get("inventory", Label.LabelStyle.class);
        SkinSkinStyles.lSmall = skin_skin.get("small", Label.LabelStyle.class);
        SkinSkinStyles.lVerySmall = skin_skin.get("very-small", Label.LabelStyle.class);
        SkinSkinStyles.lDefault = skin_skin.get("default", Label.LabelStyle.class);
        SkinSkinStyles.lDebt = skin_skin.get("debt", Label.LabelStyle.class);
        SkinSkinStyles.lSmallRed = skin_skin.get("small-red", Label.LabelStyle.class);
        SkinSkinStyles.lSubtitle = skin_skin.get("subtitle", Label.LabelStyle.class);
        SkinSkinStyles.lBig = skin_skin.get("big", Label.LabelStyle.class);
        SkinSkinStyles.lRegion = skin_skin.get("region", Label.LabelStyle.class);
        SkinSkinStyles.lDebtSubtitle = skin_skin.get("debt-subtitle", Label.LabelStyle.class);
        SkinSkinStyles.lSubtitleAuthor = skin_skin.get("subtitle-author", Label.LabelStyle.class);
        SkinSkinStyles.lCashWhite = skin_skin.get("cash-white", Label.LabelStyle.class);
        SkinSkinStyles.lCashSubtitle = skin_skin.get("cash-subtitle", Label.LabelStyle.class);
        SkinSkinStyles.lSmallGray = skin_skin.get("small-gray", Label.LabelStyle.class);
        SkinSkinStyles.lTravelSubtitle = skin_skin.get("travel-subtitle", Label.LabelStyle.class);
        SkinSkinStyles.lGray = skin_skin.get("gray", Label.LabelStyle.class);
        SkinSkinStyles.spDefault = skin_skin.get("default", ScrollPane.ScrollPaneStyle.class);
        SkinSkinStyles.sDefaultHorizontal = skin_skin.get("default-horizontal", Slider.SliderStyle.class);
        SkinSkinStyles.tbVerySmall = skin_skin.get("very-small", TextButton.TextButtonStyle.class);
        SkinSkinStyles.tbDefault = skin_skin.get("default", TextButton.TextButtonStyle.class);
        SkinSkinStyles.tbBig = skin_skin.get("big", TextButton.TextButtonStyle.class);
        SkinSkinStyles.tfDefault = skin_skin.get("default", TextField.TextFieldStyle.class);
        SkinSkinStyles.ttDefault = skin_skin.get("default", TextTooltip.TextTooltipStyle.class);
        SkinSkinStyles.wDefault = skin_skin.get("default", Window.WindowStyle.class);
        sfx_bloodSplatter = assetManager.get("sfx/blood splatter.mp3");
        sfx_click = assetManager.get("sfx/click.mp3");
        sfx_die = assetManager.get("sfx/die.mp3");
        sfx_door = assetManager.get("sfx/door.mp3");
        sfx_drakWars = assetManager.get("sfx/drak wars.mp3");
        sfx_evilLaugh = assetManager.get("sfx/evil laugh.mp3");
        sfx_finish = assetManager.get("sfx/finish.mp3");
        sfx_grenade = assetManager.get("sfx/grenade.mp3");
        sfx_gun = assetManager.get("sfx/gun.mp3");
        sfx_heal = assetManager.get("sfx/heal.mp3");
        sfx_heeHee = assetManager.get("sfx/hee hee.mp3");
        sfx_hmm = assetManager.get("sfx/hmm.mp3");
        sfx_hurt = assetManager.get("sfx/hurt.mp3");
        sfx_inventory = assetManager.get("sfx/inventory.mp3");
        sfx_magic = assetManager.get("sfx/magic.mp3");
        sfx_money = assetManager.get("sfx/money.mp3");
        sfx_ohYeah = assetManager.get("sfx/oh yeah.mp3");
        sfx_pages = assetManager.get("sfx/pages.mp3");
        sfx_portal = assetManager.get("sfx/portal.mp3");
        sfx_slash = assetManager.get("sfx/slash.mp3");
        sfx_vampAttack = assetManager.get("sfx/vamp-attack.mp3");
        sfx_vamp = assetManager.get("sfx/vamp.mp3");
        sfx_whip = assetManager.get("sfx/whip.mp3");
        bgm_audioSample = assetManager.get("bgm/audio-sample.mp3");
        bgm_below50Hp = assetManager.get("bgm/below_50_hp.ogg");
        bgm_DobrujaAndCrisana = assetManager.get("bgm/Dobruja_and_Crisana.ogg");
        bgm_hasDebtOrMoneyInBank = assetManager.get("bgm/has_debt_or_money_in_bank.ogg");
        bgm_intro = assetManager.get("bgm/intro.ogg");
        bgm_main = assetManager.get("bgm/main.ogg");
        bgm_menuIntro = assetManager.get("bgm/menu-intro.ogg");
        bgm_menu = assetManager.get("bgm/menu.ogg");
        bgm_Moldovia = assetManager.get("bgm/Moldovia.ogg");
        bgm_noMoneyOrItemsOnHand = assetManager.get("bgm/no_money_or_items_on_hand.ogg");
        bgm_ResultsINTRO = assetManager.get("bgm/Results_INTRO.ogg");
        bgm_ResultsLOOP = assetManager.get("bgm/Results_LOOP.ogg");
        bgm_Transylvania = assetManager.get("bgm/Transylvania.ogg");
        bgm_underAttack = assetManager.get("bgm/under_attack.ogg");
        bgm_WallachiaAndBukovina = assetManager.get("bgm/Wallachia_and_Bukovina.ogg");
    }

    public static class SpineBlood {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static Animation animationStand;

        public static EventData eventSfxBloodSplatter;

        public static EventData eventSfxDrakWars;

        public static BoneData boneRoot;

        public static BoneData boneBloodDrip01;

        public static BoneData boneBloodDrip02;

        public static SlotData slotWhite;

        public static SlotData slotBlood01;

        public static SlotData slotBlood02;

        public static SlotData slotBlood03;

        public static SlotData slotBlood04;

        public static SlotData slotBlood05;

        public static SlotData slotBloodDrip01;

        public static SlotData slotBloodDrip02;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineIntro {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static Animation animationStand;

        public static BoneData boneRoot;

        public static SlotData slotSpineIntro;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineLogo {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static Animation animationStand;

        public static EventData eventSfxHeeHee;

        public static EventData eventSfxHmm;

        public static EventData eventSfxOhYeah;

        public static BoneData boneRoot;

        public static BoneData boneLogoRaeleus;

        public static BoneData boneSpineZebra;

        public static BoneData boneSpineShroom;

        public static BoneData boneSpineBefluffly;

        public static SlotData slotWhite;

        public static SlotData slotLogoRaeleus;

        public static SlotData slotSpineZebra;

        public static SlotData slotSpineShroom;

        public static SlotData slotSpineBefluffly;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SkinSkinStyles {
        public static Button.ButtonStyle bDefault;

        public static Label.LabelStyle lCash;

        public static Label.LabelStyle lInventory;

        public static Label.LabelStyle lSmall;

        public static Label.LabelStyle lVerySmall;

        public static Label.LabelStyle lDefault;

        public static Label.LabelStyle lDebt;

        public static Label.LabelStyle lSmallRed;

        public static Label.LabelStyle lSubtitle;

        public static Label.LabelStyle lBig;

        public static Label.LabelStyle lRegion;

        public static Label.LabelStyle lDebtSubtitle;

        public static Label.LabelStyle lSubtitleAuthor;

        public static Label.LabelStyle lCashWhite;

        public static Label.LabelStyle lCashSubtitle;

        public static Label.LabelStyle lSmallGray;

        public static Label.LabelStyle lTravelSubtitle;

        public static Label.LabelStyle lGray;

        public static ScrollPane.ScrollPaneStyle spDefault;

        public static Slider.SliderStyle sDefaultHorizontal;

        public static TextButton.TextButtonStyle tbVerySmall;

        public static TextButton.TextButtonStyle tbDefault;

        public static TextButton.TextButtonStyle tbBig;

        public static TextField.TextFieldStyle tfDefault;

        public static TextTooltip.TextTooltipStyle ttDefault;

        public static Window.WindowStyle wDefault;
    }

    public static class Values {
        public static float jumpVelocity = 10.0f;

        public static String name = "Raeleus";

        public static boolean godMode = true;

        public static int id = 10;
    }
}
