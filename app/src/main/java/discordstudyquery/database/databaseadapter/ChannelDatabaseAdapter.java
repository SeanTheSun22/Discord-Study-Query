package discordstudyquery.database.databaseadapter;

public class ChannelDatabaseAdapter extends AbstractDatabaseAdapter {
    private Long pingRoleID = -1L;
    private Long moderatorRoleID = -1L;
    public ChannelDatabaseAdapter(Long id) {super(id);}
    public ChannelDatabaseAdapter(Long id, Long pingRoleID, Long moderatorRoleID) {
        super(id);
        this.pingRoleID = pingRoleID;
        this.moderatorRoleID = moderatorRoleID;
    }
    public Long getPingRoleID() {return pingRoleID;}
    public Long getModeratorRoleID() {return moderatorRoleID;}
}
