package com.example.constant;

/**
 * @author Yushun Shao
 * @date 2024/4/14 16:03
 */
public enum TransportType {
    /**
     *
     */
    ROAD_TRANSPORT("公路运输"),
    RAILWAY_TRANSPORT("铁路运输"),
    MARITIME_SHIPPING("海运"),
    AIR_FREIGHT("空运"),
    MULTIMODAL_TRANSPORT("多式联运");
    private final String description;
    TransportType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
