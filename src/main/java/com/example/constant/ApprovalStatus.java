package com.example.constant;

/**
 * @author Yushun Shao
 * @date 2024/3/6 14:33
 */
public enum ApprovalStatus {
    /**
     *
     */
    PENDING("待审批"),
    APPROVE("审批通过"),
    REJECT("审批拒绝");
    private final String description;
    ApprovalStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
