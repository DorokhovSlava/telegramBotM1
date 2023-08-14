package com.dorokhov.telegrambotm1.model;

import lombok.*;
import lombok.experimental.Accessors;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

import javax.persistence.*;

@Data
@Getter
@Setter
@Entity
@Table(name = "user_info")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Builder
public class UserInfo implements BotApiObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private static final String MESSAGEID_FIELD = "message_id";
    private static final String FROM_FIELD = "from";
    private static final String DATE_FIELD = "date";
    private static final String CHAT_FIELD = "chat";
    private static final String FORWARDFROM_FIELD = "forward_from";
    private static final String FORWARDFROMCHAT_FIELD = "forward_from_chat";
    private static final String FORWARDDATE_FIELD = "forward_date";
    private static final String TEXT_FIELD = "text";
    private static final String ENTITIES_FIELD = "entities";
    private static final String CAPTIONENTITIES_FIELD = "caption_entities";
    private static final String AUDIO_FIELD = "audio";
    private static final String DOCUMENT_FIELD = "document";
    private static final String PHOTO_FIELD = "photo";
    private static final String STICKER_FIELD = "sticker";
    private static final String VIDEO_FIELD = "video";
    private static final String CONTACT_FIELD = "contact";
    private static final String LOCATION_FIELD = "location";
    private static final String VENUE_FIELD = "venue";
    private static final String ANIMATION_FIELD = "animation";
    private static final String PINNED_MESSAGE_FIELD = "pinned_message";
    private static final String NEWCHATMEMBERS_FIELD = "new_chat_members";
    private static final String LEFTCHATMEMBER_FIELD = "left_chat_member";
    private static final String NEWCHATTITLE_FIELD = "new_chat_title";
    private static final String NEWCHATPHOTO_FIELD = "new_chat_photo";
    private static final String DELETECHATPHOTO_FIELD = "delete_chat_photo";
    private static final String GROUPCHATCREATED_FIELD = "group_chat_created";
    private static final String REPLYTOMESSAGE_FIELD = "reply_to_message";
    private static final String VOICE_FIELD = "voice";
    private static final String CAPTION_FIELD = "caption";
    private static final String SUPERGROUPCREATED_FIELD = "supergroup_chat_created";
    private static final String CHANNELCHATCREATED_FIELD = "channel_chat_created";
    private static final String MIGRATETOCHAT_FIELD = "migrate_to_chat_id";
    private static final String MIGRATEFROMCHAT_FIELD = "migrate_from_chat_id";
    private static final String EDITDATE_FIELD = "edit_date";
    private static final String GAME_FIELD = "game";
    private static final String FORWARDFROMMESSAGEID_FIELD = "forward_from_message_id";
    private static final String INVOICE_FIELD = "invoice";
    private static final String SUCCESSFUL_PAYMENT_FIELD = "successful_payment";
    private static final String VIDEO_NOTE_FIELD = "video_note";
    private static final String AUTHORSIGNATURE_FIELD = "author_signature";
    private static final String FORWARDSIGNATURE_FIELD = "forward_signature";
    private static final String MEDIAGROUPID_FIELD = "media_group_id";
    private static final String CONNECTEDWEBSITE_FIELD = "connected_website";
    private static final String PASSPORTDATA_FIELD = "passport_data";
    private static final String FORWARDSENDERNAME_FIELD = "forward_sender_name";
    private static final String POLL_FIELD = "poll";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";
    private static final String DICE_FIELD = "dice";
    private static final String VIABOT_FIELD = "via_bot";
    private static final String SENDERCHAT_FIELD = "sender_chat";
    private static final String PROXIMITYALERTTRIGGERED_FIELD = "proximity_alert_triggered";
    private static final String MESSAGEAUTODELETETIMERCHANGED_FIELD = "message_auto_delete_timer_changed";
    private static final String ISAUTOMATICFORWARD_FIELD = "is_automatic_forward";
    private static final String HASPROTECTEDCONTENT_FIELD = "has_protected_content";
    private static final String WEBAPPDATA_FIELD = "web_app_data";
    private static final String VIDEOCHATSCHEDULED_FIELD = "video_chat_scheduled";
    private static final String VIDEOCHATSTARTED_FIELD = "video_chat_started";
    private static final String VIDEOCHATENDED_FIELD = "video_chat_ended";
    private static final String VIDEOCHATPARTICIPANTSINVITED_FIELD = "video_chat_participants_invited";
}
