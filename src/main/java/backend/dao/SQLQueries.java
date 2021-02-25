package backend.dao;

public class SQLQueries {

    public static final String SELECT_ID_OF_SPECIFIC_PHONE_NUMBER_QUERY =
            "select telegram_bot.subscribers.id from telegram_bot.subscribers where telegram_bot.subscribers.phone_number = ?";

    public static final String SELECT_ID_OF_SPECIFIC_TAG_QUERY =
            "select telegram_bot.tags.id from telegram_bot.tags where telegram_bot.tags.tag = ?";

    public static final String INSERT_PHONE_NUMBER_QUERY =
            "insert into telegram_bot.subscribers (phone_number) values (?)";

    public static final String INSERT_SPECIFIC_TAG_QUERY =
            "insert into telegram_bot.tags (tag) values (?)";

    public static final String INSERT_NUMBER_TO_TAG_LINK_QUERY =
            "insert into telegram_bot.subscriber_tags_links (subscriber_id, tag_id) values (?, ?)";

    public static final String GET_TAG_BY_NUMBER_QUERY =
            "select\n" +
            "    telegram_bot.tags.tag\n" +
            "from\n" +
            "    telegram_bot.subscriber_tags_links,\n" +
            "    telegram_bot.subscribers,\n" +
            "    telegram_bot.tags\n" +
            "where\n" +
            "    telegram_bot.subscriber_tags_links.subscriber_id = telegram_bot.subscribers.id and\n" +
            "    telegram_bot.subscriber_tags_links.tag_id = telegram_bot.tags.id and\n" +
            "    telegram_bot.subscribers.phone_number = ?";
}
