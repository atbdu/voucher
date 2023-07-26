function dc_excel(th) {
    var cds = new Map();
    var href=$(th).attr("href");
    cds.set("exl", "is_excel");
    $(".search_field").each(function () {
        var vl = $(this).val();
        var fname = $(this).attr("name");
        if (vl != null && vl != "") {
            cds.set(fname, vl)
        }
    });
    //map转object
    let obj = Object.create(null);
    for (let [k, v] of cds) {
        obj[k] = v;
    }
    href=href+"&strJSON="+encodeURIComponent(JSON.stringify(obj), 'utf-8');
    window.location.href =href;
}