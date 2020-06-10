/**
 * @Author  apple on 2017/7/26.
 */
$(function() {
    var DragChangeSize = {
        init: function() {
            var clickX, leftOffset, inxdex, nextW2, nextW;
            dragging = false;
            doc = document;
            dragBtn = $(".dragbox").find('label');
            wrapWidth = $(".dragbox").width();
            minWidth = wrapWidth * 0.1;
            //alert(dragBtn);
            this.mousedown();
            this.onmousemove();
            this.mouseup();
        },
        mousedown: function() {
            var _this = this;
            dragBtn.mousedown(function(event) {
                dragging = true;
                leftOffset = $(".dragbox").offset().left;
                index = $(this).index('label');
                //alert(index);
            });
            // dragBtnd.mousedown(function(event) {
            //     dragging = true;
            //     leftOffset = $(".dragbox").offset().left;
            //     index = 1;
            // });
        },
        onmousemove: function() {
            $(doc).mousemove(function(e) {
                if (dragging) {
                    //----------------------------------------------------------------
                    clickX = e.pageX;
                    //$("#test").text('鼠标位置：' + clickX);
                    //console.log(index);
                    // var w = $("#result").width();
                    // var w_d = $("#c").width();
                    //判断拖动的第几个按钮
                    if (index == 0) {
                        //第一个拖动按钮左边不出界
                        if (clickX > leftOffset + minWidth&&clickX<$("#b").width() ) {
                            dragBtn.eq(index).css('left', clickX - 7 - leftOffset + 'px');
                            //按钮移动
                            $('#a').width(clickX - leftOffset + 'px');
                            nextW2 = clickX - leftOffset;
                             $('#code_area').width(wrapWidth - nextW2-$("#b").width()-$("#c").width()-$("#c").width()-20+ 'px');
                        }
                        // else if(clickX>dragBtn.eq(index).prev().width()){
                        //     dragBtn.eq(index).css('left', '0px');
                        // }

                    }

                    if (index == 7) {
                        //第二个拖动按钮左边不出界
                        if (clickX > $("#code_area").position().left + minWidth &&clickX<wrapWidth - 30 ) {
                            dragBtn.eq(index).css('left', clickX - 7 - leftOffset + 'px');
                            //按钮移动
                            $('#code_area').width(clickX - $('#a').width() - $("#c").width() -leftOffset + 'px');
                            nextW2 = clickX - leftOffset;
                            $('#b').width(wrapWidth - nextW2-$("#c").width()- $("#c").width()-20+ 'px');
                        }
                        // else if(clickX>dragBtnc.eq(index).prev().width()){
                        //     dragBtn.eq(index).css('left', '0px');
                        // }

                    }


                }
            });

        },
        mouseup: function() {
            $(doc).mouseup(function(e) {
                dragging = false;
                e.cancelBubble = true; //禁止事件冒泡
            })
        }
    };
    DragChangeSize.init();
})