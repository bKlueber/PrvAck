public class ActionOption {
    private String label;
    private String trigger; //this could be a sceneID or a world state command like LOOTING

    public ActionOption(String label, String trigger) {
        this.label = label;
        this.trigger = trigger;
    }

    public String getLabel() {
        return label;
    }

    public String getTrigger() {
        return trigger;
    }
}