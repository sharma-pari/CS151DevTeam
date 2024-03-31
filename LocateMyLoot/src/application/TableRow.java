package application;

class TableRowData {
    private String category;
    private String location;

    public TableRowData(String category, String location) {
        this.category = category;
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public String getLocation() {
        return location;
    }
}
