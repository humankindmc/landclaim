package me.rileycalhoun.landclaim.storage;

import java.io.File;
import java.io.IOException;

public class LangFile extends YamlFile {

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
        super(dataFolder, "lang.yml");
        registerVariables();
    }

    private void reloadFile() throws IOException {
        getYamlDocument().reload();
    }

    private void registerVariables() {
        /* Generic Town Command */
        NO_PERMISSION = getYamlDocument().getString("no-permission");
        TOWN_OFFICER_REQUIRED = getYamlDocument().getString("town-officer-required");
        TOWN_MAYOR_REQUIRED = getYamlDocument().getString("town-mayor-required");
        TOWN_REQUIRED = getYamlDocument().getString("town-required");
        TOWN_DOES_NOT_EXIST = getYamlDocument().getString("town-does-not-exist");
        PLAYER_NOT_ONLINE = getYamlDocument().getString("player-not-online");
        TOWN_NOT_ALLOWED = getYamlDocument().getString("town-not-allowed");
        PLAYER_NOT_ALLOWED = getYamlDocument().getString("player-not-allowed");
        SOMETHING_WENT_WRONG = getYamlDocument().getString("something-went-wrong");

        /* Town Create */
        TOWN_CREATED = getYamlDocument().getString("town-created");
        PLAYER_CREATE_TOWN = getYamlDocument().getString("player-create-town");
        TOWN_ALREADY_EXISTS = getYamlDocument().getString("town-already-exists");
        TOWN_ILLEGAL_NAME = getYamlDocument().getString("town-illegal-name");

        /* Town Disband */
        PLAYER_DISBAND_TOWN = getYamlDocument().getString("town-disbanded");
        TOWN_DISBAND_CONFIRMATION = getYamlDocument().getString("town-disband-confirmation");
        TOWN_ILLEGAL_CONFIRMATION = getYamlDocument().getString("town-illegal-confirmation");

        /* Town Joined */
        TOWN_JOINED = getYamlDocument().getString("town-joined");
        PLAYER_JOINED = getYamlDocument().getString("player-joined");
        NOT_INVITED = getYamlDocument().getString("not-invited");

        /* Town Leave */
        TOWN_LEFT = getYamlDocument().getString("town-left");
        PLAYER_LEFT = getYamlDocument().getString("player-left");
        MUST_DISBAND = getYamlDocument().getString("must-disband");

        /* Town Invite */
        TOWN_INVITE = getYamlDocument().getString("town-invite");
        PLAYER_INVITE = getYamlDocument().getString("player-invite");
        CANNOT_INVITE_SELF = getYamlDocument().getString("cannot-invite-self");
        PLAYER_ALREADY_IN_TOWN = getYamlDocument().getString("player-already-in-town");

        /* Town Claim */
        CHUNK_ALREADY_CLAIMED = getYamlDocument().getString("chunk-already-claimed");
        CHUNK_NOT_BORDERING = getYamlDocument().getString("chunk-not-bordering");
        UNABLE_TO_CLAIM_CHUNK = getYamlDocument().getString("unable-to-claim-chunk");
        CLAIM_SUCCESS = getYamlDocument().getString("claim-success");

        /* Town Unclaim */
        CHUNK_NOT_CLAIMED = getYamlDocument().getString("chunk-not-claimed");
        CHUNK_UNCLAIMED = getYamlDocument().getString("chunk-unclaimed");

        /* Town Promote */
        PLAYER_NOT_IN_TOWN = getYamlDocument().getString("player-not-in-town");
        CANNOT_PROMOTE_SELF = getYamlDocument().getString("cannot-promote-self");
        TOWN_PROMOTE_CONFIRMATION_OFFICER = getYamlDocument().getString("town-promote-confirmation-officer");
        TOWN_PROMOTE_CONFIRMATION_MAYOR = getYamlDocument().getString("town-promote-confirmation-mayor");
        PROMOTE_SUCCESS = getYamlDocument().getString("promote-success");
        PLAYER_PROMOTED = getYamlDocument().getString("player-promoted");

        /* Town Demote */
        TOWN_DEMOTE_CONFIRMATION = getYamlDocument().getString("town-demote-confirmation");
        CANNOT_DEMOTE_SELF = getYamlDocument().getString("cannot-demote-self");
        CANNOT_DEMOTE_PAST_CITIZEN = getYamlDocument().getString("cannot-demote-past-citizen");
        DEMOTE_SUCCESS = getYamlDocument().getString("demote-success");
        PLAYER_DEMOTED = getYamlDocument().getString("player-demoted");

        /* Town Kick */
        KICK_SUCCESS = getYamlDocument().getString("kick-success");
        PLAYER_KICKED = getYamlDocument().getString("player-kicked");

        /* Town Info */
        TOWN_INFO = String.join(
                "\n",
                getYamlDocument().getStringList("town-info")
        );
    }

}
