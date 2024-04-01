function deleteFinancingInfoByCompanyId(companyId) {
    return $axios({
        url: `/financingInfo/${companyId}/delete`,
        method: 'post',
    });
}

function getFinancingInfoPage(params) {
    return $axios({
        url: '/financingInfo/page',
        method: 'get',
        params,
    });
}

function getFinancingInfoById(id) {
    return $axios({
        url: `/financingInfo/${id}`,
        method: 'get',
    });
}

function deleteFinancingInfoById(id) {
    return $axios({
        url: `/financingInfo/${id}/delete`,
        method: 'post',
    });
}

function getFinancingInfoByCompanyId(id, params) {
    return $axios({
        url: `/financingInfo/company/${id}`,
        method: 'get',
        params
    });
}