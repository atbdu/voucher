<script >
        var bids = '${(bids)!}';
        var bidsarr = "";
        if(bids != null && bids != ""){
            bidsarr = bids.split(",");

            // console.log(bids);
            $("[whref]").css("display","none");
            $("a[ajax_url]").css("display","none");

            $("[whref]").each( function() {
                var obj = $(this);
                var whref = obj.attr("whref")
                var url = jiequ(whref);
                // console.log(obj.innerText+"--"+url);
                var flag = false;
                $.each(bidsarr, function(i,val){
                    if(url == val){
                        flag = true;
                        return;
                    }
                });
                if(flag){
                    $("[whref]").css("display","block");
                }else{
                    obj.remove();
                }
                // console.log(obj.attr("whref"));
            });
            $("a[ajax_url]").each( function() {
                var obj = $(this);
                var ajax_url = obj.attr("ajax_url")
                var url = jiequ(ajax_url);
                var flag = false;
                $.each(bidsarr, function(i,val){
                    if(url == val){
                        flag = true;
                        return;
                    }
                });
                if(flag){
                    $("[ajax_url]").css("display","block");
                }else{
                    obj.parent().remove();
                }
                // console.log(obj.attr("ajax_url"));
            });
            function jiequ(url) {
                console.log(url.substring(url.indexOf("=")+1,url.indexOf("&") > 0?url.indexOf("&"):url.length));
                return url.substring(url.indexOf("=")+1,url.indexOf("&") > 0?url.indexOf("&"):url.length);
            }
        }else{
            $("[whref]").each( function() {
                var obj = $(this);
                obj.remove();
            });
            $("a[ajax_url]").each( function() {
                var obj = $(this);
                obj.parent().remove();
            });
        }
</script>