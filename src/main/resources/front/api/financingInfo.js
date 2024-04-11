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

function getFinancingInfoByFinancialInstitutionId(id, params) {
    return $axios({
        url: `/financingInfo/financialInstitution/${id}`,
        method: 'get',
        params
    });
}

function createFinancingInfo(params) {
    return $axios({
        url: `/financingInfo/save`,
        method: 'post',
        data: params
    });
}

function repayFinancingInfo(id) {
    return $axios({
        url: `/financingInfo/${id}/repay`,
        method: 'post',
    });
}

function loanFinancingInfo(id) {
    return $axios({
        url: `/financingInfo/${id}/loan`,
        method: 'post',
    });
}

function approvalFinancingInfo(id, params) {
    return $axios({
        url: `/financingInfo/${id}/approval`,
        method: 'post',
        params
    });
}