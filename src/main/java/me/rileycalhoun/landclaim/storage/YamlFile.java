package me.rileycalhoun.landclaim.storage;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public abstract class YamlFile {

    private YamlDocument yamlDocument;

    protected YamlFile(File dataFolder, String fileName) throws IOException, NullPointerException {
        InputStream inputStream = getClass().getResourceAsStream("/configs/" + fileName);
        if (inputStream == null) {
            throw new NullPointerException("Could not find configs/" + fileName);
        }

        this.yamlDocument = YamlDocument.create(
                new File(dataFolder, fileName), inputStream,
                GeneralSettings.DEFAULT,
                LoaderSettings.builder().setAutoUpdate(true).build(),
                DumperSettings.DEFAULT,
                UpdaterSettings.builder()
                        .setVersioning(new BasicVersioning("version"))
                        .setOptionSorting(UpdaterSettings.OptionSorting.SORT_BY_DEFAULTS)
                        .build()
        );

        yamlDocument.update();
        yamlDocument.save();
    }

    protected YamlDocument getYamlDocument() {
        return yamlDocument;
    }

}
