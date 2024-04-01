function getSupplyChainPage(params) {
    return $axios({
        url: '/supplyChain/page',
        method: 'get',
        params,
    });
}