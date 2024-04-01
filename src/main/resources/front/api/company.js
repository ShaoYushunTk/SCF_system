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