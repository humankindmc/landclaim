package me.rileycalhoun.landclaim.config;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class LangFile {

    private final YamlDocument langFile;

    /* Generic Town Commands */
    public String NO_PERMISSION,
            TOWN_REQUIRED,
            TOWN_OFFICER_REQUIRED,
            TOWN_MAYOR_REQUIRED,
            TOWN_DOES_NOT_EXIST,
            PLAYER_NOT_ONLINE,
            TOWN_NOT_ALLOWED,
            PLAYER_NOT_ALLOWED,
            SOMETHING_WENT_WRONG;

    /* Town Created */
    public String TOWN_CREATED, PLAYER_CREATE_TOWN, TOWN_ALREADY_EXISTS, TOWN_ILLEGAL_NAME;

    /* Town Disbanded */
    public String PLAYER_DISBAND_TOWN, TOWN_DISBAND_CONFIRMATION, TOWN_ILLEGAL_CONFIRMATION;

    /* Chunk Claimed */
    public String CHUNK_ALREADY_CLAIMED,
        CHUNK_NOT_BORDERING,
        UNABLE_TO_CLAIM_CHUNK,
        CLAIM_SUCCESS;

    /* Chunk Unclaimed */
    public String CHUNK_NOT_CLAIMED, CHUNK_UNCLAIMED;

    /* Town Joined */
    public String TOWN_JOINED, PLAYER_JOINED, NOT_INVITED;

    /* Town Left */
    public String TOWN_LEFT, PLAYER_LEFT, MUST_DISBAND;

    /* Town Invite */
    public String TOWN_INVITE,
            PLAYER_INVITE,
            CANNOT_INVITE_SELF,
            PLAYER_ALREADY_IN_TOWN;

    /* Town Promote */
    public String PLAYER_NOT_IN_TOWN,
            CANNOT_PROMOTE_SELF,
            TOWN_PROMOTE_CONFIRMATION_OFFICER,
            TOWN_PROMOTE_CONFIRMATION_MAYOR,
            PROMOTE_SUCCESS,
            PLAYER_PROMOTED;

    /* Town Demote */
    public String TOWN_DEMOTE_CONFIRMATION,
            CANNOT_DEMOTE_SELF,
            CANNOT_DEMOTE_PAST_CITIZEN,
            DEMOTE_SUCCESS,
            PLAYER_DEMOTED;

    public String PLAYER_KICKED,
            KICK_SUCCESS;

    /* Town Info */
    public String TOWN_INFO;

    public LangFile(File dataFolder) throws IOException, NullPointerException {
        InputStream inputStream = getClass().getResourceAsStream("/configs/lang.yml");
        if (inputStream == null) {
            throw new NullPointerException("Could not find configs/lang.yml");
        }

        this.langFile = YamlDocument.create(
                new File(dataFolder, "lang.yml"), inputStream,
                GeneralSettings.DEFAULT,
                LoaderSettings.builder().setAutoUpdate(true).build(),
                DumperSettings.DEFAULT,
                UpdaterSettings.builder()
                        .setVersioning(new BasicVersioning("version"))
                        .setOptionSorting(UpdaterSettings.OptionSorting.SORT_BY_DEFAULTS)
                        .build()
        );

        langFile.update();
        langFile.save();

        registerVariables();
    }

    private void reloadFile() throws IOException {
        langFile.reload();
    }

    private void registerVariables() {
        /* Generic Town Command */
        NO_PERMISSION = this.langFile.getString("no-permission");
        TOWN_OFFICER_REQUIRED = this.langFile.getString("town-officer-required");
        TOWN_MAYOR_REQUIRED = this.langFile.getString("town-mayor-required");
        TOWN_REQUIRED = this.langFile.getString("town-required");
        TOWN_DOES_NOT_EXIST = this.langFile.getString("town-does-not-exist");
        PLAYER_NOT_ONLINE = this.langFile.getString("player-not-online");
        TOWN_NOT_ALLOWED = this.langFile.getString("town-not-allowed");
        PLAYER_NOT_ALLOWED = this.langFile.getString("player-not-allowed");
        SOMETHING_WENT_WRONG = this.langFile.getString("something-went-wrong");

        /* Town Create */
        TOWN_CREATED = this.langFile.getString("town-created");
        PLAYER_CREATE_TOWN = this.langFile.getString("player-create-town");
        TOWN_ALREADY_EXISTS = this.langFile.getString("town-already-exists");
        TOWN_ILLEGAL_NAME = this.langFile.getString("town-illegal-name");

        /* Town Disband */
        PLAYER_DISBAND_TOWN = this.langFile.getString("town-disbanded");
        TOWN_DISBAND_CONFIRMATION = this.langFile.getString("town-disband-confirmation");
        TOWN_ILLEGAL_CONFIRMATION = this.langFile.getString("town-illegal-confirmation");

        /* Town Joined */
        TOWN_JOINED = this.langFile.getString("town-joined");
        PLAYER_JOINED = this.langFile.getString("player-joined");
        NOT_INVITED = this.langFile.getString("not-invited");

        /* Town Leave */
        TOWN_LEFT = this.langFile.getString("town-left");
        PLAYER_LEFT = this.langFile.getString("player-left");
        MUST_DISBAND = this.langFile.getString("must-disband");

        /* Town Invite */
        TOWN_INVITE = this.langFile.getString("town-invite");
        PLAYER_INVITE = this.langFile.getString("player-invite");
        CANNOT_INVITE_SELF = this.langFile.getString("cannot-invite-self");
        PLAYER_ALREADY_IN_TOWN = this.langFile.getString("player-already-in-town");

        /* Town Claim */
        CHUNK_ALREADY_CLAIMED = this.langFile.getString("chunk-already-claimed");
        CHUNK_NOT_BORDERING = this.langFile.getString("chunk-not-bordering");
        UNABLE_TO_CLAIM_CHUNK = this.langFile.getString("unable-to-claim-chunk");
        CLAIM_SUCCESS = this.langFile.getString("claim-success");

        /* Town Unclaim */
        CHUNK_NOT_CLAIMED = this.langFile.getString("chunk-not-claimed");
        CHUNK_UNCLAIMED = this.langFile.getString("chunk-unclaimed");

        /* Town Promote */
        PLAYER_NOT_IN_TOWN = this.langFile.getString("player-not-in-town");
        CANNOT_PROMOTE_SELF = this.langFile.getString("cannot-promote-self");
        TOWN_PROMOTE_CONFIRMATION_OFFICER = this.langFile.getString("town-promote-confirmation-officer");
        TOWN_PROMOTE_CONFIRMATION_MAYOR = this.langFile.getString("town-promote-confirmation-mayor");
        PROMOTE_SUCCESS = this.langFile.getString("promote-success");
        PLAYER_PROMOTED = this.langFile.getString("player-promoted");

        /* Town Demote */
        TOWN_DEMOTE_CONFIRMATION = this.langFile.getString("town-demote-confirmation");
        CANNOT_DEMOTE_SELF = this.langFile.getString("cannot-demote-self");
        CANNOT_DEMOTE_PAST_CITIZEN = this.langFile.getString("cannot-demote-past-citizen");
        DEMOTE_SUCCESS = this.langFile.getString("demote-success");
        PLAYER_DEMOTED = this.langFile.getString("player-demoted");

        /* Town Kick */
        KICK_SUCCESS = this.langFile.getString("kick-success");
        PLAYER_KICKED = this.langFile.getString("player-kicked");

        /* Town Info */
        TOWN_INFO = String.join("\n", this.langFile.getStringList("town-info"));
    }

}
