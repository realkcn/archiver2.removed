<%@ page language="java" pageEncoding="UTF-8" session="false" %>
<script type="text/javascript"> $(function () {
    if ($.browser.msie && parseInt($.browser.version, 10) === 6) {
        $('.row div[class^="span"]:last-child').addClass("last-child");
        $('[class="span"]').addClass("margin-left-20");
        $(':button[class="btn"], :reset[class="btn"], :submit[class="btn"], input[type="button"]').addClass("button-reset");
        $(":checkbox").addClass("input-checkbox");
        $('[class^="icon-"], [class=" icon-"]').addClass("icon-sprite");
        $(".pagination li:first-child a").addClass("pagination-first-child")
    }
}) </script>