package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.HorizontalSlidingPane;
import com.mygdx.game.actors.LevelItem;
import com.mygdx.game.actors.TaskItem;
import com.mygdx.game.manager.AssetsManager;

public class TaskScreen extends ParentScreen2D {
    private Image backgroundImage;

    HorizontalSlidingPane slidingMenuPure;

    private int LINE_MENU_ITEM_COUNT = 6;

    @Override
    public void show() {
        super.show();
        backgroundImage = new Image(new TextureRegion((Texture) AssetsManager.getInstance().assets.get("level_background.png")));
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(backgroundImage);
        slidingMenuPure = new HorizontalSlidingPane();

        // Считаем ширину иконки уровня исходя из желаемого количества иконок по ширине экрана
        // Ширина = Ширина экрана / желаемое количество - (отступ слева + отступ справа)
        float itemWidth = Gdx.app.getGraphics().getWidth() / LINE_MENU_ITEM_COUNT - 40;

        // Создаем 4 секции с иконками выбора уровня
        // В каждой секции будет 2 строки иконок по 6 в каждой
        // Расставляем иконки по сетке с помощью виджета Table
        for (int section = 0; section < 4; section++) {
            Table table = new Table();
            for (int i = 0; i < 1; i++) {
                table.row();
                for (int j = 0; j < 2; j++) {
                    // (20,20,60,20) - отступы сверху, слева, снизу, справа
                    table.add(new TaskItem(itemWidth, itemWidth)).pad(20, 20, 60, 20);
                }
            }
            // Добавляем секцию в наш контейнер
            slidingMenuPure.addWidget(table);
        }

        stage.addActor(slidingMenuPure);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}


