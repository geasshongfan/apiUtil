package mybatis.com.apli.vo;

import java.math.BigDecimal;
import java.util.Date;

public class ProductCarSkuColorVo {
    private Integer id;

    private String carNo;

    private Integer skuId;

    private Integer colorFacadeId;

    private Integer colorInteriorId;

    private Integer inventoryNum;

    private BigDecimal extraPrice;

    private String imagePaths;

    private String memo;

    private Boolean isDeleted;

    private Date createdTime;

    private Integer creatorId;

    private Integer updatorId;

    private Date updatedTime;

    private Byte status;

    private Date onlineTime;

    private Date offlineTime;

    private String deviceNum;

    private Boolean asCover;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo == null ? null : carNo.trim();
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getColorFacadeId() {
        return colorFacadeId;
    }

    public void setColorFacadeId(Integer colorFacadeId) {
        this.colorFacadeId = colorFacadeId;
    }

    public Integer getColorInteriorId() {
        return colorInteriorId;
    }

    public void setColorInteriorId(Integer colorInteriorId) {
        this.colorInteriorId = colorInteriorId;
    }

    public Integer getInventoryNum() {
        return inventoryNum;
    }

    public void setInventoryNum(Integer inventoryNum) {
        this.inventoryNum = inventoryNum;
    }

    public BigDecimal getExtraPrice() {
        return extraPrice;
    }

    public void setExtraPrice(BigDecimal extraPrice) {
        this.extraPrice = extraPrice;
    }

    public String getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(String imagePaths) {
        this.imagePaths = imagePaths == null ? null : imagePaths.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getUpdatorId() {
        return updatorId;
    }

    public void setUpdatorId(Integer updatorId) {
        this.updatorId = updatorId;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(Date onlineTime) {
        this.onlineTime = onlineTime;
    }

    public Date getOfflineTime() {
        return offlineTime;
    }

    public void setOfflineTime(Date offlineTime) {
        this.offlineTime = offlineTime;
    }

    public String getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(String deviceNum) {
        this.deviceNum = deviceNum == null ? null : deviceNum.trim();
    }

    public Boolean getAsCover() {
        return asCover;
    }

    public void setAsCover(Boolean asCover) {
        this.asCover = asCover;
    }
}