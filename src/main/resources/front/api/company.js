function getCompanyPage(params) {
    return $axios({
        url: '/company/page',
        method: 'get',
        params,
    });
}

function createCompany(params) {
    return $axios({
        url: '/company/save',
        method: 'post',
        data: { ...params },
    });
}

function updateCompanyBasic(id, params) {
    return $axios({
        url: `/company/${id}/updateBasic`,
        method: 'post',
        data: { ...params },
    });
}

function getCompanyDetail(id) {
    return $axios({
        url: `/company/${id}`,
        method: 'get',
    });
}

function deleteCompanyById(id) {
    return $axios({
        url: `/company/${id}/delete`,
        method: 'post',
    });
}

function getCompanyCreditRatingById(id) {
    return $axios({
        url: `/coreEnterprise/${id}/creditRating`,
        method: 'get',
    });
}

function getCompanyList(params) {
    return $axios({
        url: `/company/list`,
        method: 'get',
        params
    });
}
