function getCompanyAssetPage(params) {
    return $axios({
        url: '/companyAsset/page',
        method: 'get',
        params,
    });
}

function getCompanyAssetById(id) {
    return $axios({
        url: `/companyAsset/${id}`,
        method: 'get',
    });
}