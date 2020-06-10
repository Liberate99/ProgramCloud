function JStrackEvent(e, t) {
    _gaq.push(["_trackEvent", "editor", e, t])
}
function JSsetEditMode(e) {
    return Scratch.FlashApp.isEditMode = e, e ? app.navigate("editor", {
        trigger: !0,
        replace: !0
    }) : Scratch.FlashApp.model.id == Scratch.INIT_DATA.PROJECT.model["id"] ? app.navigate("player", {
        trigger: !0,
        replace: !0
    }) : JSredirectTo(Scratch.FlashApp.model.id, !1, {title: Scratch.FlashApp.model.get("title")}), !0
}
function JSsetProjectBanner(e, t) {
    var n = Math.max(4e3, e.split(" ").length * 250);
    options = {
        timeout: n,
        waitForMove: !0
    }, e += '<iframe class="iframeshim" frameborder="0" scrolling="no"><html><head></head><body></body></html></iframe>', humane.el = $("div.humane")[0], notification ? notification.remove(function () {
        notification = humane.log(e, options)
    }) : notification = humane.log(e, options)
}
function JSsetPresentationMode(e) {
    return e ? app.navigate("fullscreen", {trigger: !0}) : app.navigate(Scratch.FlashApp.isEditMode ? "editor" : "player", {trigger: !0}), !0
}
function JSeditTitle(e) {
    Scratch.FlashApp.model.save({title: e, visibility: "visible"})
}
function JSlogin(e, t) {
    Scratch.FlashApp.lastEditorOp = e, $("#login-dialog").modal("show"), $("#login-dialog button").show(), t && ($("#login-dialog input[name=username]").val(t), $("#login-dialog input[name=password]").val(""), $("#login-dialog input[name=password]").focus())
}
function JSjoinScratch(e) {
    Scratch.FlashApp.lastEditorOp = e, launchRegistration()
}
function JSlogout() {
    $.ajax({
        url: "/accounts/logout/", type: "POST", success: function (e, t, n) {
            window.location.href = "/"
        }
    })
}
function JSdownloadProject() {
    Scratch.FlashApp.ASobj.ASdownload()
}
function JSremixProject() {
    var e = function () {
        Scratch.FlashApp.ASobj.ASremixProject()
    };
    Scratch.INIT_DATA.HAS_NEVER_REMIXED ? $("#remix-modal").modal("show").find(".button").click(function () {
        e(), $("#remix-modal").modal("hide")
    }) : e(), _gaq.push(["_trackEvent", "project", "remix"])
}
function JSshareProject() {
    if (Scratch.INIT_DATA.IS_IP_BANNED) {
        $("#ip-mute-ban").modal();
        return
    }
    if (Scratch.INIT_DATA.PROJECT.is_permcensored) {
        var e = _.template($("#template-permacensor-reshare-dialog").html());
        $(e()).dialog({
            title: "Cannot Re-Share Project", buttons: {
                Ok: function () {
                    $(this).dialog("close")
                }
            }
        });
        return
    }
    if (!Scratch.INIT_DATA.IS_SOCIAL) {
        openResendDialogue();
        return
    }
    $.ajax({
        type: "POST", url: Scratch.FlashApp.model.url() + "share/", success: function () {
            Scratch.FlashApp.model.set({isPublished: !0}), window.location.href = "/projects/" + Scratch.FlashApp.model.get("id") + "/"
        }, error: function (e) {
            return e.errorThrown
        }
    })
}
function JSlogImageAdded(e, t, n) {
    e.length < 1 ? e = -1 : e = parseInt(e);
    var r = {file_source_model_id: e, file_source_model: "Project", file_type: t};
    r.file_source_type = n ? 2 : 3, $.ajax({
        url: "/internalapi/asset/log-asset-upload/",
        type: "POST",
        data: r,
        error: function (e) {
            return e.errorThrown
        }
    })
}
function JSredirectTo(e, t, n) {
    setTimeout(function () {
        if (!isNaN(e) || e == "editor") {
            Scratch.FlashApp.model = app.projectModel = new Scratch.ProjectThumbnail(n);
            var r = !t && e != Scratch.INIT_DATA.PROJECT.model["id"],
                i = Scratch.FlashApp.model.get("title") + " " + gettext("on Scratch"), s = "/projects/" + e;
            if (window.location.pathname == s && (t && window.location.hash == "#editor" || !t && window.location.hash != "#editor"))return;
            s += t ? "/#editor" : "", window.history && history.replaceState && !r ? (history.replaceState(n, i, s), document.title = i) : window.location.href = s;
            return
        }
        switch (e) {
            case"about":
                window.location.href = "/about/";
                break;
            case"home":
                window.location.href = "/";
                break;
            case"logout":
                window.location.href = "/accounts/logout/";
                break;
            case"mystuff":
                window.location.href = "/mystuff/";
                break;
            case"myclasses":
                window.location.href = "/educators/classes/";
                break;
            case"myclass":
                window.location.href = "/classes/" + Scratch.INIT_DATA.LOGGED_IN_USER.model.classroom_id + "/";
                break;
            case"profile":
                window.location.href = "/users/" + Scratch.LoggedInUser.get("username");
                break;
            case"settings":
                window.location.href = "/accounts/password_change/"
        }
    }, 100)
}
function JSsetFlashDragDrop(e) {
    Scratch.FlashApp.ASobj.ondragover = function (e) {
        e.preventDefault(), e.stopPropagation()
    }, Scratch.FlashApp.ASobj.ondrop = e ? handleDrop : null
}
function handleDrop(e) {
    var t = e.clientX, n = e.clientY, r = e.dataTransfer.getData("Text"), i = e.dataTransfer.getData("URL");
    r ? Scratch.FlashApp.ASobj.ASdropURL(r, t, n) : i && FA.obj.ASdropURL(i, t, n);
    var s = e.dataTransfer.files.length;
    for (var o = 0; o < s; o++)loadFile(e.dataTransfer.files[o], t, n);
    e.stopPropagation ? e.stopPropagation() : e.cancelBubble = !0
}
function loadFile(e, t, n) {
    function r(e) {
        console.log("Error loading dropped file: " + e.target.error.code)
    }

    function i(e) {
        var r = e.target.result;
        r.length > 0 && Scratch.FlashApp.ASobj.ASdropFile(s, r, t, n)
    }

    if (window.FileReader == null) {
        console.log("FileReader API not supported by this browser");
        return
    }
    var s = "name" in e ? e.name : e.fileName, o = new FileReader;
    o.onerror = r, o.onloadend = i, o.readAsDataURL(e)
}
function JSsetProjectStats(e, t, n, r) {
    $("#script-count").html(e), $("#sprite-count").html(t), n ? $("#cloud-log").show() : $("#cloud-log").hide(), r && ($("#old-scratch-project").removeClass("hide"), $("#old-scratch-project .button").attr("href", r), $("#old-scratch-project").show(), $("#new-scratch-project").hide())
}
!function (e, t, n) {
    typeof module != "undefined" ? module.exports = n(e, t) : typeof define == "function" && typeof define.amd == "object" ? define(n) : t[e] = n(e, t)
}("humane", this, function (e, t) {
    var n = window, r = document, i = {
        on: function (e, t, r) {
            "addEventListener" in n ? e.addEventListener(t, r, !1) : e.attachEvent("on" + t, r)
        }, off: function (e, t, r) {
            "removeEventListener" in n ? e.removeEventListener(t, r, !1) : e.detachEvent("on" + t, r)
        }, bind: function (e, t) {
            return function () {
                e.apply(t, arguments)
            }
        }, isArray: Array.isArray || function (e) {
            return Object.prototype.toString.call(e) === "[object Array]"
        }, config: function (e, t) {
            return e != null ? e : t
        }, transSupport: !1, useFilter: /msie [678]/i.test(navigator.userAgent), _checkTransition: function () {
            var e = r.createElement("div"), t = {webkit: "webkit", Moz: "", O: "o", ms: "MS"};
            for (var n in t)n + "Transition" in e.style && (this.vendorPrefix = t[n], this.transSupport = !0)
        }
    };
    i._checkTransition();
    var s = function (e) {
        e || (e = {}), this.queue = [], this.baseCls = e.baseCls || "humane", this.addnCls = e.addnCls || "", this.timeout = "timeout" in e ? e.timeout : 2500, this.waitForMove = e.waitForMove || !1, this.clickToClose =
            e.clickToClose || !1, this.timeoutAfterMove = e.timeoutAfterMove || !1, this.container = e.container;
        try {
            this._setupEl()
        } catch (t) {
            i.on(n, "load", i.bind(this._setupEl, this))
        }
    };
    return s.prototype = {
        constructor: s, _setupEl: function () {
            var e = r.createElement("div");
            e.style.display = "none";
            if (!this.container) {
                if (!r.body)throw"document.body is null";
                this.container = r.body
            }
            this.container.appendChild(e), this.el = e, this.removeEvent = i.bind(function () {
                this.timeoutAfterMove ? setTimeout(i.bind(this.remove, this), this.timeout) : this.remove()
            }, this), this.transEvent = i.bind(this._afterAnimation, this), this._run()
        }, _afterTimeout: function () {
            i.config(this.currentMsg.waitForMove, this.waitForMove) ? this.removeEventsSet || (i.on(r.body, "mousemove", this.removeEvent), i.on(r.body, "click", this.removeEvent), i.on(r.body, "keypress", this.removeEvent), i.on(r.body, "touchstart", this.removeEvent), this.removeEventsSet = !0) : this.remove()
        }, _run: function () {
            if (this._animating || !this.queue.length || !this.el)return;
            this._animating = !0, this.currentTimer && (clearTimeout(this.currentTimer), this.currentTimer = null);
            var e = this.queue.shift(), t = i.config(e.clickToClose, this.clickToClose);
            t && (i.on(this.el, "click", this.removeEvent), i.on(this.el, "touchstart", this.removeEvent));
            var n = i.config(e.timeout, this.timeout);
            n > 0 && (this.currentTimer = setTimeout(i.bind(this._afterTimeout, this), n)), i.isArray(e.html) && (e.html = "<ul><li>" + e.html.join("<li>") + "</ul>"), this.el.innerHTML = e.html, this.currentMsg = e, this.el.className = this.baseCls, i.transSupport ? (this.el.style.display = "block", setTimeout(i.bind(this._showMsg, this), 50)) : this._showMsg()
        }, _setOpacity: function (e) {
            if (i.useFilter)try {
                this.el.filters.item("DXImageTransform.Microsoft.Alpha").Opacity = e * 100
            } catch (t) {
            } else this.el.style.opacity = String(e)
        }, _showMsg: function () {
            var e = i.config(this.currentMsg.addnCls, this.addnCls);
            if (i.transSupport) this.el.className = this.baseCls + " " + e + " " + this.baseCls + "-animate"; else {
                var t = 0;
                this.el.className = this.baseCls + " " + e + " " + this.baseCls + "-js-animate", this._setOpacity(0), this.el.style.display = "block";
                var n = this, r = setInterval(function () {
                    t < 1 ? (t += .1, t > 1 && (t = 1), n._setOpacity(t)) : clearInterval(r)
                }, 30)
            }
        }, _hideMsg: function () {
            var e = i.config(this.currentMsg.addnCls, this.addnCls);
            if (i.transSupport) this.el.className = this.baseCls + " " + e, i.on(this.el, i.vendorPrefix ? i.vendorPrefix + "TransitionEnd" : "transitionend", this.transEvent); else var t = 1,
                n = this, r = setInterval(function () {
                    t > 0 ? (t -= .1, t < 0 && (t = 0), n._setOpacity(t)) : (n.el.className = n.baseCls + " " + e, clearInterval(r), n._afterAnimation())
                }, 30)
        }, _afterAnimation: function () {
            i.transSupport && i.off(this.el, i.vendorPrefix ? i.vendorPrefix + "TransitionEnd" : "transitionend", this.transEvent), this.currentMsg.cb && this.currentMsg.cb(), this.el.style.display = "none", this._animating = !1, this._run()
        }, remove: function (e) {
            var t = typeof e == "function" ? e : null;
            i.off(r.body, "mousemove", this.removeEvent), i.off(r.body, "click", this.removeEvent), i.off(r.body, "keypress", this.removeEvent), i.off(r.body, "touchstart", this.removeEvent), i.off(this.el, "click", this.removeEvent), i.off(this.el, "touchstart", this.removeEvent), this.removeEventsSet = !1, t && this.currentMsg && (this.currentMsg.cb = t), this._animating ? this._hideMsg() : t && t()
        }, log: function (e, t, n, r) {
            var i = {};
            if (r)for (var s in r)i[s] = r[s];
            if (typeof t == "function") n = t; else if (t)for (var s in t)i[s] = t[s];
            return i.html = e, n && (i.cb = n), this.queue.push(i), this._run(), this
        }, spawn: function (e) {
            var t = this;
            return function (n, r, i) {
                return t.log.call(t, n, r, i, e), t
            }
        }, create: function (e) {
            return new s(e)
        }
    }, new s
}), !function (e) {
    if ("object" == typeof exports && "undefined" != typeof module) module.exports = e(); else if ("function" == typeof define && define.amd) define([], e); else {
        var t;
        "undefined" != typeof window ? t = window : "undefined" != typeof global ? t = global : "undefined" != typeof self && (t = self), t.io = e()
    }
}(function () {
    var e, t, n;
    return function r(e, t, n) {
        function i(o, u) {
            if (!t[o]) {
                if (!e[o]) {
                    var a = typeof require == "function" && require;
                    if (!u && a)return a(o, !0);
                    if (s)return s(o, !0);
                    throw new Error("Cannot find module '" + o + "'")
                }
                var f = t[o] = {exports: {}};
                e[o][0].call(f.exports, function (t) {
                    var n = e[o][1][t];
                    return i(n ? n : t)
                }, f, f.exports, r, e, t, n)
            }
            return t[o].exports
        }

        var s = typeof require == "function" && require;
        for (var o = 0; o < n.length; o++)i(n[o]);
        return i
    }({
        1: [function (e, t, n) {
            t.exports = e("./lib/")
        }, {"./lib/": 2}],
        2: [function (e, t, n) {
            function a(e, t) {
                typeof e == "object" && (t = e, e = undefined), t = t || {};
                var n = r(e), i = n.source, a = n.id, f;
                return t.forceNew || t["force new connection"] || !1 === t.multiplex ? (o("ignoring socket cache for %s", i), f = s(i, t)) : (u[a] || (o("new io instance for %s", i), u[a] = s(i, t)), f = u[a]), f.socket(n.path)
            }

            var r = e("./url"), i = e("socket.io-parser"), s = e("./manager"), o = e("debug")("socket.io-client");
            t.exports = n = a;
            var u = n.managers = {};
            n.protocol = i.protocol, n.connect = a, n.Manager = e("./manager"), n.Socket = e("./socket")
        }, {"./manager": 3, "./socket": 5, "./url": 6, debug: 10, "socket.io-parser": 44}],
        3: [function (e, t, n) {
            function d(e, t) {
                if (!(this instanceof d))return new d(e, t);
                e && "object" == typeof e && (t = e, e = undefined), t = t || {}, t.path = t.path || "/socket.io", this.nsps = {}, this.subs = [], this.opts = t, this.reconnection(t.reconnection !== !1), this.reconnectionAttempts(t.reconnectionAttempts || Infinity), this.reconnectionDelay(t.reconnectionDelay || 1e3), this.reconnectionDelayMax(t.reconnectionDelayMax || 5e3), this.randomizationFactor(t.randomizationFactor || .5), this.backoff = new p({
                    min: this.reconnectionDelay(),
                    max: this.reconnectionDelayMax(),
                    jitter: this.randomizationFactor()
                }), this.timeout(null == t.timeout ? 2e4 : t.timeout), this.readyState = "closed", this.uri = e, this.connected = [], this.encoding = !1, this.packetBuffer = [], this.encoder = new u.Encoder, this.decoder = new u.Decoder, this.autoConnect = t.autoConnect !== !1, this.autoConnect && this.open()
            }

            var r = e("./url"), i = e("engine.io-client"), s = e("./socket"), o = e("component-emitter"),
                u = e("socket.io-parser"), a = e("./on"), f = e("component-bind"), l = e("object-component"),
                c = e("debug")("socket.io-client:manager"), h = e("indexof"), p = e("backo2");
            t.exports = d, d.prototype.emitAll = function () {
                this.emit.apply(this, arguments);
                for (var e in this.nsps)this.nsps[e].emit.apply(this.nsps
                    [e], arguments)
            }, d.prototype.updateSocketIds = function () {
                for (var e in this.nsps)this.nsps[e].id = this.engine.id
            }, o(d.prototype), d.prototype.reconnection = function (e) {
                return arguments.length ? (this._reconnection = !!e, this) : this._reconnection
            }, d.prototype.reconnectionAttempts = function (e) {
                return arguments.length ? (this._reconnectionAttempts = e, this) : this._reconnectionAttempts
            }, d.prototype.reconnectionDelay = function (e) {
                return arguments.length ? (this._reconnectionDelay = e, this.backoff && this.backoff.setMin(e), this) : this._reconnectionDelay
            }, d.prototype.randomizationFactor = function (e) {
                return arguments.length ? (this._randomizationFactor = e, this.backoff && this.backoff.setJitter(e), this) : this._randomizationFactor
            }, d.prototype.reconnectionDelayMax = function (e) {
                return arguments.length ? (this._reconnectionDelayMax = e, this.backoff && this.backoff.setMax(e), this) : this._reconnectionDelayMax
            }, d.prototype.timeout = function (e) {
                return arguments.length ? (this._timeout = e, this) : this._timeout
            }, d.prototype.maybeReconnectOnOpen = function () {
                !this.reconnecting && this._reconnection && this.backoff.attempts === 0 && this.reconnect()
            }, d.prototype.open = d.prototype.connect = function (e) {
                c("readyState %s", this.readyState);
                if (~this.readyState.indexOf("open"))return this;
                c("opening %s", this.uri), this.engine = i(this.uri, this.opts);
                var t = this.engine, n = this;
                this.readyState = "opening", this.skipReconnect = !1;
                var r = a(t, "open", function () {
                    n.onopen(), e && e()
                }), s = a(t, "error", function (t) {
                    c("connect_error"), n.cleanup(), n.readyState = "closed", n.emitAll("connect_error", t);
                    if (e) {
                        var r = new Error("Connection error");
                        r.data = t, e(r)
                    } else n.maybeReconnectOnOpen()
                });
                if (!1 !== this._timeout) {
                    var o = this._timeout;
                    c("connect attempt will timeout after %d", o);
                    var u = setTimeout(function () {
                        c("connect attempt timed out after %d", o), r.destroy(), t.close(), t.emit("error", "timeout"), n.emitAll("connect_timeout", o)
                    }, o);
                    this.subs.push({
                        destroy: function () {
                            clearTimeout(u)
                        }
                    })
                }
                return this.subs.push(r), this.subs.push(s), this
            }, d.prototype.onopen = function () {
                c("open"), this.cleanup(), this.readyState = "open", this.emit("open");
                var e = this.engine;
                this.subs.push(a(e, "data", f(this, "ondata"))), this.subs.push(a(this.decoder, "decoded", f(this, "ondecoded"))), this.subs.push(a(e, "error", f(this, "onerror"))), this.subs.push(a(e, "close", f(this, "onclose")))
            }, d.prototype.ondata = function (e) {
                this.decoder.add(e)
            }, d.prototype.ondecoded = function (e) {
                this.emit("packet", e)
            }, d.prototype.onerror = function (e) {
                c("error", e), this.emitAll("error", e)
            }, d.prototype.socket = function (e) {
                var t = this.nsps[e];
                if (!t) {
                    t = new s(this, e), this.nsps[e] = t;
                    var n = this;
                    t.on("connect", function () {
                        t.id = n.engine.id, ~h(n.connected, t) || n.connected.push(t)
                    })
                }
                return t
            }, d.prototype.destroy = function (e) {
                var t = h(this.connected, e);
                ~t && this.connected.splice(t, 1);
                if (this.connected.length)return;
                this.close()
            }, d.prototype.packet = function (e) {
                c("writing packet %j", e);
                var t = this;
                t.encoding ? t.packetBuffer.push(e) : (t.encoding = !0, this.encoder.encode(e, function (e) {
                    for (var n = 0; n < e.length; n++)t.engine.write(e[n]);
                    t.encoding = !1, t.processPacketQueue()
                }))
            }, d.prototype.processPacketQueue = function () {
                if (this.packetBuffer.length > 0 && !this.encoding) {
                    var e = this.packetBuffer.shift();
                    this.packet(e)
                }
            }, d.prototype.cleanup = function () {
                var e;
                while (e = this.subs.shift())e.destroy();
                this.packetBuffer = [], this.encoding = !1, this.decoder.destroy()
            }, d.prototype.close = d.prototype.disconnect = function () {
                this.skipReconnect = !0, this.backoff.reset(), this.readyState = "closed", this.engine && this.engine.close()
            }, d.prototype.onclose = function (e) {
                c("close"), this.cleanup(), this.backoff.reset(), this.readyState = "closed", this.emit("close", e), this._reconnection && !this.skipReconnect && this.reconnect()
            }, d.prototype.reconnect = function () {
                if (this.reconnecting || this.skipReconnect)return this;
                var e = this;
                if (this.backoff.attempts >= this._reconnectionAttempts) c("reconnect failed"), this.backoff.reset(), this.emitAll("reconnect_failed"), this.reconnecting = !1; else {
                    var t = this.backoff.duration();
                    c("will wait %dms before reconnect attempt", t), this.reconnecting = !0;
                    var n = setTimeout(function () {
                        if (e.skipReconnect)return;
                        c("attempting reconnect"), e.emitAll("reconnect_attempt", e.backoff.attempts), e.emitAll("reconnecting", e.backoff.attempts);
                        if (e.skipReconnect)return;
                        e.open(function (t) {
                            t ? (c("reconnect attempt error"), e.reconnecting = !1, e.reconnect(), e.emitAll("reconnect_error", t.data)) : (c("reconnect success"), e.onreconnect())
                        })
                    }, t);
                    this.subs.push({
                        destroy: function () {
                            clearTimeout(n)
                        }
                    })
                }
            }, d.prototype.onreconnect = function () {
                var e = this.backoff.attempts;
                this.reconnecting = !1, this.backoff.reset(), this.updateSocketIds(), this.emitAll("reconnect", e)
            }
        }, {
            "./on": 4,
            "./socket": 5,
            "./url": 6,
            backo2: 7,
            "component-bind": 8,
            "component-emitter": 9,
            debug: 10,
            "engine.io-client": 11,
            indexof: 40,
            "object-component": 41,
            "socket.io-parser": 44
        }],
        4: [function (e, t, n) {
            function r(e, t, n) {
                return e.on(t, n), {
                    destroy: function () {
                        e.removeListener(t, n)
                    }
                }
            }

            t.exports = r
        }, {}],
        5: [function (e, t, n) {
            function h(e, t) {
                this.io = e, this.nsp = t, this.json = this, this.ids = 0, this.acks = {}, this.io.autoConnect && this.open(), this.receiveBuffer = [], this.sendBuffer = [], this.connected = !1, this.disconnected = !0
            }

            var r = e("socket.io-parser"), i = e("component-emitter"), s = e("to-array"), o = e("./on"),
                u = e("component-bind"), a = e("debug")("socket.io-client:socket"), f = e("has-binary");
            t.exports = n = h;
            var l = {
                connect: 1,
                connect_error: 1,
                connect_timeout: 1,
                disconnect: 1,
                error: 1,
                reconnect: 1,
                reconnect_attempt: 1,
                reconnect_failed: 1,
                reconnect_error: 1,
                reconnecting: 1
            }, c = i.prototype.emit;
            i(h.prototype), h.prototype.subEvents = function () {
                if (this.subs)return;
                var e = this.io;
                this.subs = [o(e, "open", u(this, "onopen")), o(e, "packet", u(this, "onpacket")), o(e, "close", u(this, "onclose"))]
            }, h.prototype.open = h.prototype.connect = function () {
                return this.connected ? this : (this.subEvents(), this.io.open(), "open" == this.io.readyState && this.onopen(), this)
            }, h.prototype.send = function () {
                var e = s(arguments);
                return e.unshift("message"), this.emit.apply(this, e), this
            }, h.prototype.emit = function (e) {
                if (l.hasOwnProperty(e))return c.apply(this, arguments), this;
                var t = s(arguments), n = r.EVENT;
                f(t) && (n = r.BINARY_EVENT);
                var i = {
                    type: n, data: t
                };
                return "function" == typeof t[t.length - 1] && (a("emitting packet with ack id %d", this.ids), this.acks[this.ids] = t.pop(), i.id = this.ids++), this.connected ? this.packet(i) : this.sendBuffer.push(i), this
            }, h.prototype.packet = function (e) {
                e.nsp = this.nsp, this.io.packet(e)
            }, h.prototype.onopen = function () {
                a("transport is open - connecting"), "/" != this.nsp && this.packet({type: r.CONNECT})
            }, h.prototype.onclose = function (e) {
                a("close (%s)", e), this.connected = !1, this.disconnected = !0, delete this.id, this.emit("disconnect", e)
            }, h.prototype.onpacket = function (e) {
                if (e.nsp != this.nsp)return;
                switch (e.type) {
                    case r.CONNECT:
                        this.onconnect();
                        break;
                    case r.EVENT:
                        this.onevent(e);
                        break;
                    case r.BINARY_EVENT:
                        this.onevent(e);
                        break;
                    case r.ACK:
                        this.onack(e);
                        break;
                    case r.BINARY_ACK:
                        this.onack(e);
                        break;
                    case r.DISCONNECT:
                        this.ondisconnect();
                        break;
                    case r.ERROR:
                        this.emit("error", e.data)
                }
            }, h.prototype.onevent = function (e) {
                var t = e.data || [];
                a("emitting event %j", t), null != e.id && (a("attaching ack callback to event"), t.push(this.ack(e.id))), this.connected ? c.apply(this, t) : this.receiveBuffer.push(t)
            }, h.prototype.ack = function (e) {
                var t = this, n = !1;
                return function () {
                    if (n)return;
                    n = !0;
                    var i = s(arguments);
                    a("sending ack %j", i);
                    var o = f(i) ? r.BINARY_ACK : r.ACK;
                    t.packet({type: o, id: e, data: i})
                }
            }, h.prototype.onack = function (e) {
                a("calling ack %s with %j", e.id, e.data);
                var t = this.acks[e.id];
                t.apply(this, e.data), delete this.acks[e.id]
            }, h.prototype.onconnect = function () {
                this.connected = !0, this.disconnected = !1, this.emit("connect"), this.emitBuffered()
            }, h.prototype.emitBuffered = function () {
                var e;
                for (e = 0; e < this.receiveBuffer.length; e++)c.apply(this, this.receiveBuffer[e]);
                this.receiveBuffer = [];
                for (e = 0; e < this.sendBuffer.length; e++)this.packet(this.sendBuffer[e]);
                this.sendBuffer = []
            }, h.prototype.ondisconnect = function () {
                a("server disconnect (%s)", this.nsp), this.destroy(), this.onclose("io server disconnect")
            }, h.prototype.destroy = function () {
                if (this.subs) {
                    for (var e = 0; e < this.subs.length; e++)this.subs[e].destroy();
                    this.subs = null
                }
                this.io.destroy(this)
            }, h.prototype.close = h.prototype.disconnect = function () {
                return this.connected && (a("performing disconnect (%s)", this.nsp), this.packet({type: r.DISCONNECT})), this.destroy(), this.connected && this.onclose("io client disconnect"), this
            }
        }, {
            "./on": 4,
            "component-bind": 8,
            "component-emitter": 9,
            debug: 10,
            "has-binary": 36,
            "socket.io-parser": 44,
            "to-array": 48
        }],
        6: [function (e, t, n) {
            (function (n) {
                function s(e, t) {
                    var s = e, t = t || n.location;
                    return null == e && (e = t.protocol + "//" + t.host), "string" == typeof e && ("/" == e.charAt(0) && ("/" == e.charAt(1) ? e = t.protocol + e : e = t.hostname + e), /^(https?|wss?):\/\//.test(e) || (i("protocol-less url %s", e), "undefined" != typeof t ? e = t.protocol + "//" + e : e = "https://" + e), i("parse %s", e), s = r(e)), s.port || (/^(http|ws)$/.test(s.protocol) ? s.port = "80" : /^(http|ws)s$/.test(s.protocol) && (s.port = "443")), s.path = s.path || "/", s.id = s.protocol + "://" + s.host + ":" + s.port, s.href = s.protocol + "://" + s.host + (t && t.port == s.port ? "" : ":" + s.port), s
                }

                var r = e("parseuri"), i = e("debug")("socket.io-client:url");
                t.exports = s
            }).call(this, typeof self != "undefined" ? self : typeof window != "undefined" ? window : {})
        }, {debug: 10, parseuri: 42}],
        7: [function (e, t, n) {
            function r(e) {
                e = e || {}, this.ms = e.min || 100, this.max = e.max || 1e4, this.factor = e.factor || 2, this.jitter = e.jitter > 0 && e.jitter <= 1 ? e.jitter : 0, this.attempts = 0
            }

            t.exports = r, r.prototype.duration = function () {
                var e = this.ms * Math.pow(this.factor, this.attempts++);
                if (this.jitter) {
                    var t = Math.random(), n = Math.floor(t * this.jitter * e);
                    e = (Math.floor(t * 10) & 1) == 0 ? e - n : e + n
                }
                return Math.min(e, this.max) | 0
            }, r.prototype.reset = function () {
                this.attempts = 0
            }, r.prototype.setMin = function (e) {
                this.ms = e
            }, r.prototype.setMax = function (e) {
                this.max = e
            }, r.prototype.setJitter = function (e) {
                this.jitter = e
            }
        }, {}],
        8: [function (e, t, n) {
            var r = [].slice;
            t.exports = function (e, t) {
                "string" == typeof t && (t = e[t]);
                if ("function" != typeof t)throw new Error("bind() requires a function");
                var n = r.call(arguments, 2);
                return function () {
                    return t.apply(e, n.concat(r.call(arguments)))
                }
            }
        }, {}],
        9: [function (e, t, n) {
            function r(e) {
                if (e)return i(e)
            }

            function i(e) {
                for (var t in r.prototype)e[t] = r.prototype[t];
                return e
            }

            t.exports = r, r.prototype.on = r.prototype.addEventListener = function (e, t) {
                return this._callbacks = this._callbacks || {}, (this._callbacks[e] = this._callbacks[e] || []).push(t), this
            }, r.prototype.once = function (e, t) {
                function r() {
                    n.off(e, r), t.apply(this, arguments)
                }

                var n = this;
                return this._callbacks = this._callbacks || {}, r.fn = t, this.on(e, r), this
            }, r.prototype.off = r.prototype.removeListener = r.prototype.removeAllListeners = r.prototype.removeEventListener = function (e, t) {
                this._callbacks = this._callbacks || {};
                if (0 == arguments.length)return this._callbacks = {}, this;
                var n = this._callbacks[e];
                if (!n)return this;
                if (1 == arguments.length)return delete this._callbacks[e], this;
                var r;
                for (var i = 0; i < n.length; i++) {
                    r = n[i];
                    if (r === t || r.fn === t) {
                        n.splice(i, 1);
                        break
                    }
                }
                return this
            }, r.prototype.emit = function (e) {
                this._callbacks = this._callbacks || {};
                var t = [].slice.call(arguments, 1), n = this._callbacks[e];
                if (n) {
                    n = n.slice(0);
                    for (var r = 0, i = n.length; r < i; ++r)n[r].apply(this, t)
                }
                return this
            }, r.prototype.listeners = function (e) {
                return this._callbacks = this._callbacks || {}, this._callbacks[e] || []
            }, r.prototype.hasListeners = function (e) {
                return !!this.listeners(e).length
            }
        }, {}],
        10: [function (e, t, n) {
            function r(e) {
                return r.enabled(e) ? function (t) {
                    t = i(t);
                    var n = new Date, s = n - (r[e] || n);
                    r[e] = n, t = e + " " + t + " +" + r.humanize(s), window.console && console.log && Function.prototype.apply.call(console.log, console, arguments)
                } : function () {
                }
            }

            function i(e) {
                return e instanceof Error ? e.stack || e.message : e
            }

            t.exports = r, r.names = [], r.skips = [], r.enable = function (e) {
                try {
                    localStorage.debug = e
                } catch (t) {
                }
                var n = (e || "").split(/[\s,]+/), i = n.length;
                for (var s = 0; s < i; s++)e = n[s].replace("*", ".*?"), e[0] === "-" ? r.skips.push(new RegExp("^" + e.substr(1) + "$")) : r.names.push(new RegExp("^" + e + "$"))
            }, r.disable = function () {
                r.enable("")
            }, r.humanize = function (e) {
                var t = 1e3, n = 6e4, r = 60 * n;
                return e >= r ? (e / r).toFixed(1) + "h" : e >= n ? (e / n).toFixed(1) + "m" : e >= t ? (e / t | 0) + "s" : e + "ms"
            }, r.enabled = function (e) {
                for (var t = 0, n = r.skips.length; t < n; t++)if (r.skips[t].test(e))return !1;
                for (var t = 0, n = r.names.length;
                     t < n; t++)if (r.names[t].test(e))return !0;
                return !1
            };
            try {
                window.localStorage && r.enable(localStorage.debug)
            } catch (s) {
            }
        }, {}],
        11: [function (e, t, n) {
            t.exports = e("./lib/")
        }, {"./lib/": 12}],
        12: [function (e, t, n) {
            t.exports = e("./socket"), t.exports.parser = e("engine.io-parser")
        }, {"./socket": 13, "engine.io-parser": 25}],
        13: [function (e, t, n) {
            (function (n) {
                function c() {
                }

                function h(e, t) {
                    if (!(this instanceof h))return new h(e, t);
                    t = t || {}, e && "object" == typeof e && (t = e, e = null), e && (e = a(e), t.host = e.host, t.secure = e.protocol == "https" || e.protocol == "wss", t.port = e.port, e.query && (t.query = e.query)), this.secure = null != t.secure ? t.secure : n.location && "https:" == location.protocol;
                    if (t.host) {
                        var r = t.host.split(":");
                        t.hostname = r.shift(), r.length ? t.port = r.pop() : t.port || (t.port = this.secure ? "443" : "80")
                    }
                    this.agent = t.agent || !1, this.hostname = t.hostname || (n.location ? location.hostname : "localhost"), this.port = t.port || (n.location && location.port ? location.port : this.secure ? 443 : 80), this.query = t.query || {}, "string" == typeof this.query && (this.query = l.decode(this.query)), this.upgrade = !1 !== t.upgrade, this.path = (t.path || "/engine.io").replace(/\/$/, "") + "/", this.forceJSONP = !!t.forceJSONP, this.jsonp = !1 !== t.jsonp, this.forceBase64 = !!t.forceBase64, this.enablesXDR = !!t.enablesXDR, this.timestampParam = t.timestampParam || "t", this.timestampRequests = t.timestampRequests, this.transports = t.transports || ["polling", "websocket"], this.readyState = "", this.writeBuffer = [], this.callbackBuffer = [], this.policyPort = t.policyPort || 843, this.rememberUpgrade = t.rememberUpgrade || !1, this.binaryType = null, this.onlyBinaryUpgrades = t.onlyBinaryUpgrades, this.pfx = t.pfx || null, this.key = t.key || null, this.passphrase = t.passphrase || null, this.cert = t.cert || null, this.ca = t.ca || null, this.ciphers = t.ciphers || null, this.rejectUnauthorized = t.rejectUnauthorized || null, this.open()
                }

                function p(e) {
                    var t = {};
                    for (var n in e)e.hasOwnProperty(n) && (t[n] = e[n]);
                    return t
                }

                var r = e("./transports"), i = e("component-emitter"), s = e("debug")("engine.io-client:socket"),
                    o = e("indexof"), u = e("engine.io-parser"), a = e("parseuri"), f = e("parsejson"),
                    l = e("parseqs");
                t.exports = h, h.priorWebsocketSuccess = !1, i(h.prototype), h.protocol = u.protocol, h.Socket = h, h.Transport = e("./transport"), h.transports = e("./transports"), h.parser = e("engine.io-parser"), h.prototype.createTransport = function (e) {
                    s('creating transport "%s"', e);
                    var t = p(this.query);
                    t.EIO = u.protocol, t.transport = e, this.id && (t.sid = this.id);
                    var n = new r[e]({
                        agent: this.agent,
                        hostname: this.hostname,
                        port: this.port,
                        secure: this.secure,
                        path: this.path,
                        query: t,
                        forceJSONP: this.forceJSONP,
                        jsonp: this.jsonp,
                        forceBase64: this.forceBase64,
                        enablesXDR: this.enablesXDR,
                        timestampRequests: this.timestampRequests,
                        timestampParam: this.timestampParam,
                        policyPort: this.policyPort,
                        socket: this,
                        pfx: this.pfx,
                        key: this.key,
                        passphrase: this.passphrase,
                        cert: this.cert,
                        ca: this.ca,
                        ciphers: this.ciphers,
                        rejectUnauthorized: this.rejectUnauthorized
                    });
                    return n
                }, h.prototype.open = function () {
                    var e;
                    if (this.rememberUpgrade && h.priorWebsocketSuccess && this.transports.indexOf("websocket") != -1) e = "websocket"; else {
                        if (0 == this.transports.length) {
                            var t = this;
                            setTimeout(function () {
                                t.emit("error", "No transports available")
                            }, 0);
                            return
                        }
                        e = this.transports[0]
                    }
                    this.readyState = "opening";
                    var e;
                    try {
                        e = this.createTransport(e)
                    } catch (n) {
                        this.transports.shift(), this.open();
                        return
                    }
                    e.open(), this.setTransport(e)
                }, h.prototype.setTransport = function (e) {
                    s("setting transport %s", e.name);
                    var t = this;
                    this.transport && (s("clearing existing transport %s", this.transport.name), this.transport.removeAllListeners()), this.transport = e, e.on("drain", function () {
                        t.onDrain()
                    }).on("packet", function (e) {
                        t.onPacket(e)
                    }).on("error", function (e) {
                        t.onError(e)
                    }).on("close", function () {
                        t.onClose("transport close")
                    })
                }, h.prototype.probe = function (e) {
                    function i() {
                        if (r.onlyBinaryUpgrades) {
                            var i = !this.supportsBinary && r.transport.supportsBinary;
                            n = n || i
                        }
                        if (n)return;
                        s('probe transport "%s" opened', e), t.send([{
                            type: "ping",
                            data: "probe"
                        }]), t.once("packet", function (i) {
                            if (n)return;
                            if ("pong" == i.type && "probe" == i.data) {
                                s('probe transport "%s" pong', e), r.upgrading = !0, r.emit("upgrading", t);
                                if (!t)return;
                                h.priorWebsocketSuccess = "websocket" == t.name, s('pausing current transport "%s"', r.transport.name), r.transport.pause(function () {
                                    if (n)return;
                                    if ("closed" == r.readyState)return;
                                    s("changing transport and sending upgrade packet"), c(), r.setTransport(t), t.send([{type: "upgrade"}]), r.emit("upgrade", t), t = null, r.upgrading = !1, r.flush()
                                })
                            } else {
                                s('probe transport "%s" failed', e);
                                var o = new Error("probe error");
                                o.transport = t.name, r.emit("upgradeError", o)
                            }
                        })
                    }

                    function o() {
                        if (n)return;
                        n = !0, c(), t.close(), t = null
                    }

                    function u(n) {
                        var i = new Error("probe error: " + n);
                        i.transport = t.name, o(), s('probe transport "%s" failed because of error: %s', e, n), r.emit("upgradeError", i)
                    }

                    function a() {
                        u("transport closed")
                    }

                    function f() {
                        u("socket closed")
                    }

                    function l(e) {
                        t && e.name != t.name && (s('"%s" works - aborting "%s"', e.name, t.name), o())
                    }

                    function c() {
                        t.removeListener("open", i), t.removeListener("error", u), t.removeListener("close", a), r.removeListener("close", f), r.removeListener("upgrading", l)
                    }

                    s('probing transport "%s"', e);
                    var t = this.createTransport(e, {probe: 1}), n = !1, r = this;
                    h.priorWebsocketSuccess = !1, t.once("open", i), t.once("error", u), t.once("close", a), this.once("close", f), this.once("upgrading", l), t.open()
                }, h.prototype.onOpen = function () {
                    s("socket open"), this.readyState = "open", h.priorWebsocketSuccess = "websocket" == this.transport.name, this.emit("open"), this.flush();
                    if ("open" == this.readyState && this.upgrade && this.transport.pause) {
                        s("starting upgrade probes");
                        for (var e = 0, t = this.upgrades.length; e < t; e++)this.probe(this.upgrades[e])
                    }
                }, h.prototype.onPacket = function (e) {
                    if ("opening" == this.readyState || "open" == this.readyState) {
                        s('socket receive: type "%s", data "%s"', e.type, e.data), this.emit("packet", e), this.emit("heartbeat");
                        switch (e.type) {
                            case"open":
                                this.onHandshake(f(e.data));
                                break;
                            case"pong":
                                this.setPing();
                                break;
                            case"error":
                                var t = new Error("server error");
                                t.code = e.data, this.emit("error", t);
                                break;
                            case"message":
                                this.emit("data", e.data), this.emit("message", e.data)
                        }
                    } else s('packet received with socket readyState "%s"'
                        , this.readyState)
                }, h.prototype.onHandshake = function (e) {
                    this.emit("handshake", e), this.id = e.sid, this.transport.query.sid = e.sid, this.upgrades = this.filterUpgrades(e.upgrades), this.pingInterval = e.pingInterval, this.pingTimeout = e.pingTimeout, this.onOpen();
                    if ("closed" == this.readyState)return;
                    this.setPing(), this.removeListener("heartbeat", this.onHeartbeat), this.on("heartbeat", this.onHeartbeat)
                }, h.prototype.onHeartbeat = function (e) {
                    clearTimeout(this.pingTimeoutTimer);
                    var t = this;
                    t.pingTimeoutTimer = setTimeout(function () {
                        if ("closed" == t.readyState)return;
                        t.onClose("ping timeout")
                    }, e || t.pingInterval + t.pingTimeout)
                }, h.prototype.setPing = function () {
                    var e = this;
                    clearTimeout(e.pingIntervalTimer), e.pingIntervalTimer = setTimeout(function () {
                        s("writing ping packet - expecting pong within %sms", e.pingTimeout), e.ping(), e.onHeartbeat(e.pingTimeout)
                    }, e.pingInterval)
                }, h.prototype.ping = function () {
                    this.sendPacket("ping")
                }, h.prototype.onDrain = function () {
                    for (var e = 0; e < this.prevBufferLen; e++)this.callbackBuffer[e] && this.callbackBuffer[e]();
                    this.writeBuffer.splice(0, this.prevBufferLen), this.callbackBuffer.splice(0, this.prevBufferLen), this.prevBufferLen = 0, this.writeBuffer.length == 0 ? this.emit("drain") : this.flush()
                }, h.prototype.flush = function () {
                    "closed" != this.readyState && this.transport.writable && !this.upgrading && this.writeBuffer.length && (s("flushing %d packets in socket", this.writeBuffer.length), this.transport.send(this.writeBuffer), this.prevBufferLen = this.writeBuffer.length, this.emit("flush"))
                }, h.prototype.write = h.prototype.send = function (e, t) {
                    return this.sendPacket("message", e, t), this
                }, h.prototype.sendPacket = function (e, t, n) {
                    if ("closing" == this.readyState || "closed" == this.readyState)return;
                    var r = {type: e, data: t};
                    this.emit("packetCreate", r), this.writeBuffer.push(r), this.callbackBuffer.push(n), this.flush()
                }, h.prototype.close = function () {
                    if ("opening" == this.readyState || "open" == this.readyState) {
                        this.readyState = "closing";
                        var e = this;

                        function t() {
                            e.onClose("forced close"), s("socket closing - telling transport to close"), e.transport.close()
                        }

                        function n() {
                            e.removeListener("upgrade", n), e.removeListener("upgradeError", n), t()
                        }

                        function r() {
                            e.once("upgrade", n), e.once("upgradeError", n)
                        }

                        this.writeBuffer.length ? this.once("drain", function () {
                            this.upgrading ? r() : t()
                        }) : this.upgrading ? r() : t()
                    }
                    return this
                }, h.prototype.onError = function (e) {
                    s("socket error %j", e), h.priorWebsocketSuccess = !1, this.emit("error", e), this.onClose("transport error", e)
                }, h.prototype.onClose = function (e, t) {
                    if ("opening" == this.readyState || "open" == this.readyState || "closing" == this.readyState) {
                        s('socket close with reason: "%s"', e);
                        var n = this;
                        clearTimeout(this.pingIntervalTimer), clearTimeout(this.pingTimeoutTimer), setTimeout(function () {
                            n.writeBuffer = [], n.callbackBuffer = [], n.prevBufferLen = 0
                        }, 0), this.transport.removeAllListeners("close"), this.transport.close(), this.transport.removeAllListeners(), this.readyState = "closed", this.id = null, this.emit("close", e, t)
                    }
                }, h.prototype.filterUpgrades = function (e) {
                    var t = [];
                    for (var n = 0, r = e.length; n < r; n++)~o(this.transports, e[n]) && t.push(e[n]);
                    return t
                }
            }).call(this, typeof self != "undefined" ? self : typeof window != "undefined" ? window : {})
        }, {
            "./transport": 14,
            "./transports": 15,
            "component-emitter": 9,
            debug: 22,
            "engine.io-parser": 25,
            indexof: 40,
            parsejson: 32,
            parseqs: 33,
            parseuri: 34
        }],
        14: [function (e, t, n) {
            function s(e) {
                this.path = e.path, this.hostname = e.hostname, this.port = e.port, this.secure = e.secure, this.query = e.query, this.timestampParam = e.timestampParam, this.timestampRequests = e.timestampRequests, this.readyState = "", this.agent = e.agent || !1, this.socket = e.socket, this.enablesXDR = e.enablesXDR, this.pfx = e.pfx, this.key = e.key, this.passphrase = e.passphrase, this.cert = e.cert, this.ca = e.ca, this.ciphers = e.ciphers, this.rejectUnauthorized = e.rejectUnauthorized
            }

            var r = e("engine.io-parser"), i = e("component-emitter");
            t.exports = s, i(s.prototype), s.timestamps = 0, s.prototype.onError = function (e, t) {
                var n = new Error(e);
                return n.type = "TransportError", n.description = t, this.emit("error", n), this
            }, s.prototype.open = function () {
                if ("closed" == this.readyState || "" == this.readyState) this.readyState = "opening", this.doOpen();
                return this
            }, s.prototype.close = function () {
                if ("opening" == this.readyState || "open" == this.readyState) this.doClose(), this.onClose();
                return this
            }, s.prototype.send = function (e) {
                if ("open" != this.readyState)throw new Error("Transport not open");
                this.write(e)
            }, s.prototype.onOpen = function () {
                this.readyState = "open", this.writable = !0, this.emit("open")
            }, s.prototype.onData = function (e) {
                var t = r.decodePacket(e, this.socket.binaryType);
                this.onPacket(t)
            }, s.prototype.onPacket = function (e) {
                this.emit("packet", e)
            }, s.prototype.onClose = function () {
                this.readyState = "closed", this.emit("close")
            }
        }, {"component-emitter": 9, "engine.io-parser": 25}],
        15: [function (e, t, n) {
            (function (t) {
                function u(e) {
                    var n, o = !1, u = !1, a = !1 !== e.jsonp;
                    if (t.location) {
                        var f = "https:" == location.protocol, l = location.port;
                        l || (l = f ? 443 : 80), o = e.hostname != location.hostname || l != e.port, u = e.secure != f
                    }
                    e.xdomain = o, e.xscheme = u, n = new r(e);
                    if ("open" in n && !e.forceJSONP)return new i(e);
                    if (!a)throw new Error("JSONP disabled");
                    return new s(e)
                }

                var r = e("xmlhttprequest"), i = e("./polling-xhr"), s = e("./polling-jsonp"), o = e("./websocket");
                n.polling = u, n.websocket = o
            }).call(this, typeof self != "undefined" ? self : typeof window != "undefined" ? window : {})
        }, {"./polling-jsonp": 16, "./polling-xhr": 17, "./websocket": 19, xmlhttprequest: 20}],
        16: [function (e, t, n) {
            (function (n) {
                function f() {
                }

                function l(e) {
                    r.call(this, e), this.query = this.query || {}, u || (n.___eio || (n.___eio = []), u = n.___eio), this.index = u.length;
                    var t = this;
                    u.push(function (e) {
                        t.onData(e)
                    }), this.query.j = this.index, n.document && n.addEventListener && n.addEventListener("beforeunload", function () {
                        t.script && (t.script.onerror = f)
                    }, !1)
                }

                var r = e("./polling"), i = e("component-inherit");
                t.exports = l;
                var s = /\n/g, o = /\\n/g, u, a = 0;
                i(l, r), l.prototype.supportsBinary = !1, l.prototype.doClose = function () {
                    this.script && (this.script.parentNode.removeChild(this.script), this.script = null), this.form && (this.form.parentNode.removeChild(this.form), this.form = null, this.iframe = null), r.prototype.doClose.call(this)
                }, l.prototype.doPoll =
                    function () {
                        var e = this, t = document.createElement("script");
                        this.script && (this.script.parentNode.removeChild(this.script), this.script = null), t.async = !0, t.src = this.uri(), t.onerror = function (t) {
                            e.onError("jsonp poll error", t)
                        };
                        var n = document.getElementsByTagName("script")[0];
                        n.parentNode.insertBefore(t, n), this.script = t;
                        var r = "undefined" != typeof navigator && /gecko/i.test(navigator.userAgent);
                        r && setTimeout(function () {
                            var e = document.createElement("iframe");
                            document.body.appendChild(e), document.body.removeChild(e)
                        }, 100)
                    }, l.prototype.doWrite = function (e, t) {
                    function f() {
                        l(), t()
                    }

                    function l() {
                        if (n.iframe)try {
                            n.form.removeChild(n.iframe)
                        } catch (e) {
                            n.onError("jsonp polling iframe removal error", e)
                        }
                        try {
                            var t = '<iframe src="javascript:0" name="' + n.iframeId + '">';
                            a = document.createElement(t)
                        } catch (e) {
                            a = document.createElement("iframe"), a.name = n.iframeId, a.src = "javascript:0"
                        }
                        a.id = n.iframeId, n.form.appendChild(a), n.iframe = a
                    }

                    var n = this;
                    if (!this.form) {
                        var r = document.createElement("form"), i = document.createElement("textarea"),
                            u = this.iframeId = "eio_iframe_" + this.index, a;
                        r.className = "socketio", r.style.position = "absolute", r.style.top = "-1000px", r.style.left = "-1000px", r.target = u, r.method = "POST", r.setAttribute("accept-charset", "utf-8"), i.name = "d", r.appendChild(i), document.body.appendChild(r), this.form = r, this.area = i
                    }
                    this.form.action = this.uri(), l(), e = e.replace(o, "\\\n"), this.area.value = e.replace(s, "\\n");
                    try {
                        this.form.submit()
                    } catch (c) {
                    }
                    this.iframe.attachEvent ? this.iframe.onreadystatechange = function () {
                        n.iframe.readyState == "complete" && f()
                    } : this.iframe.onload = f
                }
            }).call(this, typeof self != "undefined" ? self : typeof window != "undefined" ? window : {})
        }, {"./polling": 18, "component-inherit": 21}],
        17: [function (e, t, n) {
            (function (n) {
                function a() {
                }

                function f(e) {
                    i.call(this, e);
                    if (n.location) {
                        var t = "https:" == location.protocol, r = location.port;
                        r || (r = t ? 443 : 80), this.xd = e.hostname != n.location.hostname || r != e.port, this.xs = e.secure != t
                    }
                }

                function l(e) {
                    this.method = e.method || "GET", this.uri = e.uri, this.xd = !!e.xd, this.xs = !!e.xs, this.async = !1 !== e.async, this.data = undefined != e.data ? e.data : null, this.agent = e.agent, this.isBinary = e.isBinary, this.supportsBinary = e.supportsBinary, this.enablesXDR = e.enablesXDR, this.pfx = e.pfx, this.key = e.key, this.passphrase = e.passphrase, this.cert = e.cert, this.ca = e.ca, this.ciphers = e.ciphers, this.rejectUnauthorized = e.rejectUnauthorized, this.create()
                }

                function c() {
                    for (var e in l.requests)l.requests.hasOwnProperty(e) && l.requests[e].abort()
                }

                var r = e("xmlhttprequest"), i = e("./polling"), s = e("component-emitter"), o = e("component-inherit"),
                    u = e("debug")("engine.io-client:polling-xhr");
                t.exports = f, t.exports.Request = l, o(f, i), f.prototype.supportsBinary = !0, f.prototype.request = function (e) {
                    return e = e || {}, e.uri = this.uri(), e.xd = this.xd, e.xs = this.xs, e.agent = this.agent || !1, e.supportsBinary = this.supportsBinary, e.enablesXDR = this.enablesXDR, e.pfx = this.pfx, e.key = this.key, e.passphrase = this.passphrase, e.cert = this.cert, e.ca = this.ca, e.ciphers = this.ciphers, e.rejectUnauthorized = this.rejectUnauthorized, new l(e)
                }, f.prototype.doWrite = function (e, t) {
                    var n = typeof e != "string" && e !== undefined,
                        r = this.request({method: "POST", data: e, isBinary: n}), i = this;
                    r.on("success", t), r.on("error", function (e) {
                        i.onError("xhr post error", e)
                    }), this.sendXhr = r
                }, f.prototype.doPoll = function () {
                    u("xhr poll");
                    var e = this.request(), t = this;
                    e.on("data", function (e) {
                        t.onData(e)
                    }), e.on("error", function (e) {
                        t.onError("xhr poll error", e)
                    }), this.pollXhr = e
                }, s(l.prototype), l.prototype.create = function () {
                    var e = {agent: this.agent, xdomain: this.xd, xscheme: this.xs, enablesXDR: this.enablesXDR};
                    e.pfx = this.pfx, e.key = this.key, e.passphrase = this.passphrase, e.cert = this.cert, e.ca = this.ca, e.ciphers = this.ciphers, e.rejectUnauthorized = this.rejectUnauthorized;
                    var t = this.xhr = new r(e), i = this;
                    try {
                        u("xhr open %s: %s", this.method, this.uri), t.open(this.method, this.uri, this.async), this.supportsBinary && (t.responseType = "arraybuffer");
                        if ("POST" == this.method)try {
                            this.isBinary ? t.setRequestHeader("Content-type", "application/octet-stream") : t.setRequestHeader("Content-type", "text/plain;charset=UTF-8")
                        } catch (s) {
                        }
                        "withCredentials" in t && (t.withCredentials = !0), this.hasXDR() ? (t.onload = function () {
                            i.onLoad()
                        }, t.onerror = function () {
                            i.onError(t.responseText)
                        }) : t.onreadystatechange = function () {
                            if (4 != t.readyState)return;
                            200 == t.status || 1223 == t.status ? i.onLoad() : setTimeout(function () {
                                i.onError(t.status)
                            }, 0)
                        }, u("xhr data %s", this.data), t.send(this.data)
                    } catch (s) {
                        setTimeout(function () {
                            i.onError(s)
                        }, 0);
                        return
                    }
                    n.document && (this.index = l.requestsCount++, l.requests[this.index] = this)
                }, l.prototype.onSuccess = function () {
                    this.emit("success"), this.cleanup()
                }, l.prototype.onData = function (e) {
                    this.emit("data", e), this.onSuccess()
                }, l.prototype.onError = function (e) {
                    this.emit("error", e), this.cleanup(!0)
                }, l.prototype.cleanup = function (e) {
                    if ("undefined" == typeof this.xhr || null === this.xhr)return;
                    this.hasXDR() ? this.xhr.onload = this.xhr.onerror = a : this.xhr.onreadystatechange = a;
                    if (e)try {
                        this.xhr.abort()
                    } catch (t) {
                    }
                    n.document && delete l.requests[this.index], this.xhr = null
                }, l.prototype.onLoad = function () {
                    var e;
                    try {
                        var t;
                        try {
                            t = this.xhr.getResponseHeader("Content-Type").split(";")[0]
                        } catch (n) {
                        }
                        t === "application/octet-stream" ? e = this.xhr.response : this.supportsBinary ? e = "ok" : e = this.xhr.responseText
                    } catch (n) {
                        this.onError(n)
                    }
                    null != e && this.onData(e)
                }, l.prototype.hasXDR = function () {
                    return "undefined" != typeof n.XDomainRequest && !this.xs && this.enablesXDR
                }, l.prototype.abort = function () {
                    this.cleanup()
                }, n.document && (l.requestsCount = 0, l.requests = {}, n.attachEvent ? n.attachEvent("onunload", c) : n.addEventListener && n.addEventListener("beforeunload", c, !1))
            }).call(this, typeof self != "undefined" ? self : typeof window != "undefined" ? window : {})
        }, {"./polling": 18, "component-emitter": 9, "component-inherit": 21, debug: 22, xmlhttprequest: 20}],
        18: [function (e, t, n) {
            function f(e) {
                var t = e && e.forceBase64;
                if (!a || t) this.supportsBinary = !1;
                r.call(this, e)
            }

            var r = e("../transport"), i = e("parseqs"), s = e("engine.io-parser"), o = e("component-inherit"),
                u = e("debug")("engine.io-client:polling");
            t.exports = f;
            var a = function () {
                var t = e("xmlhttprequest"), n = new t({xdomain: !1});
                return null != n.responseType
            }();
            o(f, r), f.prototype.name = "polling", f.prototype.doOpen = function () {
                this.poll()
            }, f.prototype.pause = function (e) {
                function r() {
                    u("paused"), n.readyState = "paused", e()
                }

                var t = 0, n = this;
                this.readyState = "pausing";
                if (this.polling || !this.writable) {
                    var i = 0;
                    this.polling && (u("we are currently polling - waiting to pause"), i++, this.once("pollComplete", function () {
                        u("pre-pause polling complete"), --i || r()
                    })), this.writable || (u("we are currently writing - waiting to pause"), i++, this.once("drain", function () {
                        u("pre-pause writing complete"), --i || r()
                    }))
                } else r()
            }, f.prototype.poll = function () {
                u("polling"), this.polling = !0, this.doPoll(), this.emit("poll")
            }, f.prototype.onData = function (e) {
                var t = this;
                u("polling got data %s", e);
                var n = function (e, n, r) {
                    "opening" == t.readyState && t.onOpen();
                    if ("close" == e.type)return t.onClose(), !1;
                    t.onPacket(e)
                };
                s.decodePayload(e, this.socket.binaryType, n), "closed" != this.readyState && (this.polling = !1, this.emit("pollComplete"), "open" == this.readyState ? this.poll() : u('ignoring poll - transport state "%s"', this.readyState))
            }, f.prototype.doClose = function () {
                function t() {
                    u("writing close packet"), e.write([{type: "close"}])
                }

                var e = this;
                "open" == this.readyState ? (u("transport open - closing"), t()) : (u("transport not open - deferring close"), this.once("open", t))
            }, f.prototype.write = function (e) {
                var t = this;
                this.writable = !1;
                var n = function () {
                    t.writable = !0, t.emit("drain")
                }, t = this;
                s.encodePayload(e, this.supportsBinary, function (e) {
                    t.doWrite(e, n)
                })
            }, f.prototype.uri = function () {
                var e = this.query || {}, t = this.secure ? "https" : "http", n = "";
                return !1 !== this.timestampRequests && (e[this.timestampParam] = +(new Date) + "-" + r.timestamps++), !this.supportsBinary && !e.sid && (e.b64 = 1), e = i.encode(e), this.port && ("https" == t && this.port != 443 || "http" == t && this.port != 80) && (n = ":" + this.port), e.length && (e = "?" + e), t + "://" + this.hostname + n + this.path + e
            }
        }, {
            "../transport": 14,
            "component-inherit": 21,
            debug: 22,
            "engine.io-parser": 25,
            parseqs: 33,
            xmlhttprequest: 20
        }],
        19: [function (e, t, n) {
            function f(e) {
                var t = e && e.forceBase64;
                t && (this.supportsBinary = !1), r.call(this, e)
            }

            var r = e("../transport"), i = e("engine.io-parser"), s = e("parseqs"), o = e("component-inherit"),
                u = e("debug")("engine.io-client:websocket"), a = e("ws");
            t.exports = f, o(f, r), f.prototype.name = "websocket", f.prototype.supportsBinary = !0, f.prototype.doOpen = function () {
                if (!this.check())return;
                var e = this, t = this.uri(), n = void 0, r = {agent: this.agent};
                r.pfx = this.pfx, r.key = this.key, r.passphrase = this.passphrase, r.cert = this.cert, r.ca = this.ca, r.ciphers = this.ciphers, r.rejectUnauthorized = this.rejectUnauthorized, this.ws = new a(t, n, r), this.ws.binaryType === undefined && (this.supportsBinary = !1), this.ws.binaryType = "arraybuffer", this.addEventListeners()
            }, f.prototype.addEventListeners = function () {
                var e = this;
                this.ws.onopen = function () {
                    e.onOpen()
                }, this.ws.onclose = function () {
                    e.onClose()
                }, this.ws.onmessage = function (t) {
                    e.onData(t.data)
                }, this.ws.onerror = function (t) {
                    e.onError("websocket error", t)
                }
            }, "undefined" != typeof navigator && /iPad|iPhone|iPod/i.test(navigator.userAgent) && (f.prototype.onData = function (e) {
                var t = this;
                setTimeout(function () {
                    r.prototype.onData.call(t, e)
                }, 0)
            }), f.prototype.write = function (e) {
                function s() {
                    t.writable = !0, t.emit("drain")
                }

                var t = this;
                this.writable = !1;
                for (var n = 0, r = e.length; n < r; n++)i.encodePacket(e[n], this.supportsBinary, function (e) {
                    try {
                        t.ws.send(e)
                    } catch (n) {
                        u("websocket closed before onclose event")
                    }
                });
                setTimeout(s, 0)
            }, f.prototype.onClose = function () {
                r.prototype.onClose.call(this)
            }, f.prototype.doClose = function () {
                typeof this.ws != "undefined" && this.ws.close()
            }, f.prototype.uri = function () {
                var e = this.query || {}, t = this.secure ? "wss" : "ws", n = "";
                return this.port && ("wss" == t && this.port != 443 || "ws" == t && this.port != 80) && (n = ":" + this.port), this.timestampRequests && (e[this.timestampParam] = +(new Date)), this.supportsBinary || (e.b64 = 1), e = s.encode(e), e.length && (e = "?" + e), t + "://" + this.hostname + n + this.path + e
            }, f.prototype.check = function () {
                return !!a && !("__initialize" in a && this.name === f.prototype.name)
            }
        }, {"../transport": 14, "component-inherit": 21, debug: 22, "engine.io-parser": 25, parseqs: 33, ws: 35}],
        20: [function (e, t, n) {
            var r = e("has-cors");
            t.exports = function (e) {
                var t = e.xdomain, n = e.xscheme, i = e.enablesXDR;
                try {
                    if ("undefined" != typeof XMLHttpRequest && (!t || r))return new XMLHttpRequest
                } catch (s) {
                }
                try {
                    if ("undefined" != typeof XDomainRequest && !n && i)return new XDomainRequest
                } catch (s) {
                }
                if (!t)try {
                    return new ActiveXObject("Microsoft.XMLHTTP")
                } catch (s) {
                }
            }
        }, {"has-cors": 38}],
        21: [function (e, t, n) {
            t.exports = function (e, t) {
                var n = function () {
                };
                n.prototype = t.prototype, e.prototype = new n, e.prototype.constructor = e
            }
        }, {}],
        22: [function (e, t, n) {
            function r() {
                return "WebkitAppearance" in document.documentElement.style || window.console && (console.firebug || console.exception && console.table) || navigator.userAgent.toLowerCase().match(/firefox\/(\d+)/) && parseInt(RegExp.$1, 10) >= 31
            }

            function i() {
                var e = arguments, t = this.useColors;
                e[0] = (t ? "%c" : "") + this.namespace + (t ? " %c" : " ") + e[0] + (t ? "%c " : " ") + "+" + n.humanize(this.diff);
                if (!t)return e;
                var r = "color: " + this.color;
                e = [e[0], r, "color: inherit"].concat(Array.prototype.slice.call(e, 1));
                var i = 0, s = 0;
                return e[0].replace(/%[a-z%]/g, function (e) {
                    if ("%%" === e)return;
                    i++, "%c" === e && (s = i)
                }), e.splice(s, 0, r), e
            }

            function s() {
                return "object" == typeof console && "function" == typeof console.log && Function.prototype.apply.call(console.log, console, arguments)
            }

            function o(e) {
                try {
                    null == e ? localStorage.removeItem("debug") : localStorage.debug = e
                } catch (t) {
                }
            }

            function u() {
                var e;
                try {
                    e = localStorage.debug
                } catch (t) {
                }
                return e
            }

            n = t.exports = e("./debug"), n.log = s, n.formatArgs = i, n.save = o, n.load = u, n.useColors = r, n.colors = ["lightseagreen", "forestgreen", "goldenrod", "dodgerblue", "darkorchid", "crimson"], n.formatters.j = function (e) {
                return JSON.stringify(e)
            }, n.enable(u())
        }, {"./debug": 23}],
        23: [function (e, t, n) {
            function s() {
                return n.colors[r++ % n.colors.length]
            }

            function o(e) {
                function t() {
                }

                function r() {
                    var e = r, t = +(new Date), o = t - (i || t);
                    e.diff = o, e.prev = i, e.curr = t, i = t, null == e.useColors && (e.useColors = n.useColors()), null == e.color && e.useColors && (e.color = s());
                    var u = Array.prototype.slice.call(arguments);
                    u[0] = n.coerce(u[0]), "string" != typeof u[0] && (u = ["%o"].concat(
                        u));
                    var a = 0;
                    u[0] = u[0].replace(/%([a-z%])/g, function (t, r) {
                        if (t === "%%")return t;
                        a++;
                        var i = n.formatters[r];
                        if ("function" == typeof i) {
                            var s = u[a];
                            t = i.call(e, s), u.splice(a, 1), a--
                        }
                        return t
                    }), "function" == typeof n.formatArgs && (u = n.formatArgs.apply(e, u));
                    var f = r.log || n.log || console.log.bind(console);
                    f.apply(e, u)
                }

                t.enabled = !1, r.enabled = !0;
                var o = n.enabled(e) ? r : t;
                return o.namespace = e, o
            }

            function u(e) {
                n.save(e);
                var t = (e || "").split(/[\s,]+/), r = t.length;
                for (var i = 0; i < r; i++) {
                    if (!t[i])continue;
                    e = t[i].replace(/\*/g, ".*?"), e[0] === "-" ? n.skips.push(new RegExp("^" + e.substr(1) + "$")) : n.names.push(new RegExp("^" + e + "$"))
                }
            }

            function a() {
                n.enable("")
            }

            function f(e) {
                var t, r;
                for (t = 0, r = n.skips.length; t < r; t++)if (n.skips[t].test(e))return !1;
                for (t = 0, r = n.names.length; t < r; t++)if (n.names[t].test(e))return !0;
                return !1
            }

            function l(e) {
                return e instanceof Error ? e.stack || e.message : e
            }

            n = t.exports = o, n.coerce = l, n.disable = a, n.enable = u, n.enabled = f, n.humanize = e("ms"), n.names = [], n.skips = [], n.formatters = {};
            var r = 0, i
        }, {ms: 24}],
        24: [function (e, t, n) {
            function a(e) {
                var t = /^((?:\d+)?\.?\d+) *(ms|seconds?|s|minutes?|m|hours?|h|days?|d|years?|y)?$/i.exec(e);
                if (!t)return;
                var n = parseFloat(t[1]), a = (t[2] || "ms").toLowerCase();
                switch (a) {
                    case"years":
                    case"year":
                    case"y":
                        return n * u;
                    case"days":
                    case"day":
                    case"d":
                        return n * o;
                    case"hours":
                    case"hour":
                    case"h":
                        return n * s;
                    case"minutes":
                    case"minute":
                    case"m":
                        return n * i;
                    case"seconds":
                    case"second":
                    case"s":
                        return n * r;
                    case"ms":
                        return n
                }
            }

            function f(e) {
                return e >= o ? Math.round(e / o) + "d" : e >= s ? Math.round(e / s) + "h" : e >= i ? Math.round(e / i) + "m" : e >= r ? Math.round(e / r) + "s" : e + "ms"
            }

            function l(e) {
                return c(e, o, "day") || c(e, s, "hour") || c(e, i, "minute") || c(e, r, "second") || e + " ms"
            }

            function c(e, t, n) {
                if (e < t)return;
                return e < t * 1.5 ? Math.floor(e / t) + " " + n : Math.ceil(e / t) + " " + n + "s"
            }

            var r = 1e3, i = r * 60, s = i * 60, o = s * 24, u = o * 365.25;
            t.exports = function (e, t) {
                return t = t || {}, "string" == typeof e ? a(e) : t.long ? l(e) : f(e)
            }
        }, {}],
        25: [function (e, t, n) {
            (function (t) {
                function m(e, t) {
                    var r = "b" + n.packets[e.type] + e.data.data;
                    return t(r)
                }

                function g(e, t, r) {
                    if (!t)return n.encodeBase64Packet(e, r);
                    var i = e.data, s = new Uint8Array(i), o = new Uint8Array(1 + i.byteLength);
                    o[0] = h[e.type];
                    for (var u = 0; u < s.length; u++)o[u + 1] = s[u];
                    return r(o.buffer)
                }

                function y(e, t, r) {
                    if (!t)return n.encodeBase64Packet(e, r);
                    var i = new FileReader;
                    return i.onload = function () {
                        e.data = i.result, n.encodePacket(e, t, !0, r)
                    }, i.readAsArrayBuffer(e.data)
                }

                function b(e, t, r) {
                    if (!t)return n.encodeBase64Packet(e, r);
                    if (c)return y(e, t, r);
                    var i = new Uint8Array(1);
                    i[0] = h[e.type];
                    var s = new v([i.buffer, e.data]);
                    return r(s)
                }

                function w(e, t, n) {
                    var r = new Array(e.length), i = u(e.length, n), s = function (e, n, i) {
                        t(n, function (t, n) {
                            r[e] = n, i(t, r)
                        })
                    };
                    for (var o = 0; o < e.length; o++)s(o, e[o], i)
                }

                var r = e("./keys"), i = e("has-binary"), s = e("arraybuffer.slice"), o = e("base64-arraybuffer"),
                    u = e("after"), a = e("utf8"), f = navigator.userAgent.match(/Android/i),
                    l = /PhantomJS/i.test(navigator.userAgent), c = f || l;
                n.protocol = 3;
                var h = n.packets = {open: 0, close: 1, ping: 2, pong: 3, message: 4, upgrade: 5, noop: 6}, p = r(h),
                    d = {type: "error", data: "parser error"}, v = e("blob");
                n.encodePacket = function (e, n, r, i) {
                    "function" == typeof n && (i = n, n = !1), "function" == typeof r && (i = r, r = null);
                    var s = e.data === undefined ? undefined : e.data.buffer || e.data;
                    if (t.ArrayBuffer && s instanceof ArrayBuffer)return g(e, n, i);
                    if (v && s instanceof t.Blob)return b(e, n, i);
                    if (s && s.base64)return m(e, i);
                    var o = h[e.type];
                    return undefined !== e.data && (o += r ? a.encode(String(e.data)) : String(e.data)), i("" + o)
                }, n.encodeBase64Packet = function (e, r) {
                    var i = "b" + n.packets[e.type];
                    if (v && e.data instanceof v) {
                        var s = new FileReader;
                        return s.onload = function () {
                            var e = s.result.split(",")[1];
                            r(i + e)
                        }, s.readAsDataURL(e.data)
                    }
                    var o;
                    try {
                        o = String.fromCharCode.apply(null, new Uint8Array(e.data))
                    } catch (u) {
                        var a = new Uint8Array(e.data), f = new Array(a.length);
                        for (var l = 0; l < a.length; l++)f[l] = a[l];
                        o = String.fromCharCode.apply(null, f)
                    }
                    return i += t.btoa(o), r(i)
                }, n.decodePacket = function (e, t, r) {
                    if (typeof e == "string" || e === undefined) {
                        if (e.charAt(0) == "b")return n.decodeBase64Packet(e.substr(1), t);
                        if (r)try {
                            e = a.decode(e)
                        } catch (i) {
                            return d
                        }
                        var o = e.charAt(0);
                        return Number(o) != o || !p[o] ? d : e.length > 1 ? {
                            type: p[o],
                            data: e.substring(1)
                        } : {type: p[o]}
                    }
                    var u = new Uint8Array(e), o = u[0], f = s(e, 1);
                    return v && t === "blob" && (f = new v([f])), {type: p[o], data: f}
                }, n.decodeBase64Packet = function (e, n) {
                    var r = p[e.charAt(0)];
                    if (!t.ArrayBuffer)return {type: r, data: {base64: !0, data: e.substr(1)}};
                    var i = o.decode(e.substr(1));
                    return n === "blob" && v && (i = new v([i])), {type: r, data: i}
                }, n.encodePayload = function (e, t, r) {
                    function o(e) {
                        return e.length + ":" + e
                    }

                    function u(e, r) {
                        n.encodePacket(e, s ? t : !1, !0, function (e) {
                            r(null, o(e))
                        })
                    }

                    typeof t == "function" && (r = t, t = null);
                    var s = i(e);
                    if (t && s)return v && !c ? n.encodePayloadAsBlob(e, r) : n.encodePayloadAsArrayBuffer(e, r);
                    if (!e.length)return r("0:");
                    w(e, u, function (e, t) {
                        return r(t.join(""))
                    })
                }, n.decodePayload = function (e, t, r) {
                    if (typeof e != "string")return n.decodePayloadAsBinary(e, t, r);
                    typeof t == "function" && (r = t, t = null);
                    var i;
                    if (e == "")return r(d, 0, 1);
                    var s = "", o, u;
                    for (var a = 0, f = e.length; a < f; a++) {
                        var l = e.charAt(a);
                        if (":" != l) s += l; else {
                            if ("" == s || s != (o = Number(s)))return r(d, 0, 1);
                            u = e.substr(a + 1, o);
                            if (s != u.length)return r(d, 0, 1);
                            if (u.length) {
                                i = n.decodePacket(u, t, !0);
                                if (d.type == i.type && d.data == i.data)return r(d, 0, 1);
                                var c = r(i, a + o, f);
                                if (!1 === c)return
                            }
                            a += o, s = ""
                        }
                    }
                    if (s != "")return r(d, 0, 1)
                }, n.encodePayloadAsArrayBuffer = function (e, t) {
                    function r(e, t) {
                        n.encodePacket(e, !0, !0, function (e) {
                            return t(null, e)
                        })
                    }

                    if (!e.length)return t(new ArrayBuffer(0));
                    w(e, r, function (e, n) {
                        var r = n.reduce(function (e, t) {
                            var n;
                            return typeof t == "string" ? n = t.length : n = t.byteLength, e + n.toString().length + n + 2
                        }, 0), i = new Uint8Array(r), s = 0;
                        return n.forEach(function (e) {
                            var t = typeof e == "string", n = e;
                            if (t) {
                                var r = new Uint8Array(e.length);
                                for (var o = 0; o < e.length; o++)r[o] = e.charCodeAt(o);
                                n = r.buffer
                            }
                            t ? i[s++] = 0 : i[s++] = 1;
                            var u = n.byteLength.toString();
                            for (var o = 0; o < u.length; o++)i[s++] = parseInt(u[o]);
                            i[s++] = 255;
                            var r = new Uint8Array(n);
                            for (var o = 0; o < r.length; o++)i[s++] = r[o]
                        }), t(i.buffer)
                    })
                }, n.encodePayloadAsBlob = function (e, t) {
                    function r(e, t) {
                        n.encodePacket(e, !0, !0, function (e) {
                            var n = new Uint8Array(1);
                            n[0] = 1;
                            if (typeof e == "string"
                            ) {
                                var r = new Uint8Array(e.length);
                                for (var i = 0; i < e.length; i++)r[i] = e.charCodeAt(i);
                                e = r.buffer, n[0] = 0
                            }
                            var s = e instanceof ArrayBuffer ? e.byteLength : e.size, o = s.toString(),
                                u = new Uint8Array(o.length + 1);
                            for (var i = 0; i < o.length; i++)u[i] = parseInt(o[i]);
                            u[o.length] = 255;
                            if (v) {
                                var a = new v([n.buffer, u.buffer, e]);
                                t(null, a)
                            }
                        })
                    }

                    w(e, r, function (e, n) {
                        return t(new v(n))
                    })
                }, n.decodePayloadAsBinary = function (e, t, r) {
                    typeof t == "function" && (r = t, t = null);
                    var i = e, o = [], u = !1;
                    while (i.byteLength > 0) {
                        var a = new Uint8Array(i), f = a[0] === 0, l = "";
                        for (var c = 1; ; c++) {
                            if (a[c] == 255)break;
                            if (l.length > 310) {
                                u = !0;
                                break
                            }
                            l += a[c]
                        }
                        if (u)return r(d, 0, 1);
                        i = s(i, 2 + l.length), l = parseInt(l);
                        var h = s(i, 0, l);
                        if (f)try {
                            h = String.fromCharCode.apply(null, new Uint8Array(h))
                        } catch (p) {
                            var v = new Uint8Array(h);
                            h = "";
                            for (var c = 0; c < v.length; c++)h += String.fromCharCode(v[c])
                        }
                        o.push(h), i = s(i, l)
                    }
                    var m = o.length;
                    o.forEach(function (e, i) {
                        r(n.decodePacket(e, t, !0), i, m)
                    })
                }
            }).call(this, typeof self != "undefined" ? self : typeof window != "undefined" ? window : {})
        }, {
            "./keys": 26,
            after: 27,
            "arraybuffer.slice": 28,
            "base64-arraybuffer": 29,
            blob: 30,
            "has-binary": 36,
            utf8: 31
        }],
        26: [function (e, t, n) {
            t.exports = Object.keys || function (t) {
                    var n = [], r = Object.prototype.hasOwnProperty;
                    for (var i in t)r.call(t, i) && n.push(i);
                    return n
                }
        }, {}],
        27: [function (e, t, n) {
            function r(e, t, n) {
                function s(e, i) {
                    if (s.count <= 0)throw new Error("after called too many times");
                    --s.count, e ? (r = !0, t(e), t = n) : s.count === 0 && !r && t(null, i)
                }

                var r = !1;
                return n = n || i, s.count = e, e === 0 ? t() : s
            }

            function i() {
            }

            t.exports = r
        }, {}],
        28: [function (e, t, n) {
            t.exports = function (e, t, n) {
                var r = e.byteLength;
                t = t || 0, n = n || r;
                if (e.slice)return e.slice(t, n);
                t < 0 && (t += r), n < 0 && (n += r), n > r && (n = r);
                if (t >= r || t >= n || r === 0)return new ArrayBuffer(0);
                var i = new Uint8Array(e), s = new Uint8Array(n - t);
                for (var o = t, u = 0; o < n; o++, u++)s[u] = i[o];
                return s.buffer
            }
        }, {}],
        29: [function (e, t, n) {
            (function (e) {
                "use strict";
                n.encode = function (t) {
                    var n = new Uint8Array(t), r, i = n.length, s = "";
                    for (r = 0; r < i; r += 3)s += e[n[r] >> 2], s += e[(n[r] & 3) << 4 | n[r + 1] >> 4], s += e[(n[r + 1] & 15) << 2 | n[r + 2] >> 6], s += e[n[r + 2] & 63];
                    return i % 3 === 2 ? s = s.substring(0, s.length - 1) + "=" : i % 3 === 1 && (s = s.substring(0, s.length - 2) + "=="), s
                }, n.decode = function (t) {
                    var n = t.length * .75, r = t.length, i, s = 0, o, u, a, f;
                    t[t.length - 1] === "=" && (n--, t[t.length - 2] === "=" && n--);
                    var l = new ArrayBuffer(n), c = new Uint8Array(l);
                    for (i = 0; i < r; i += 4)o = e.indexOf(t[i]), u = e.indexOf(t[i + 1]), a = e.indexOf(t[i + 2]), f = e.indexOf(t[i + 3]), c[s++] = o << 2 | u >> 4, c[s++] = (u & 15) << 4 | a >> 2, c[s++] = (a & 3) << 6 | f & 63;
                    return l
                }
            })("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/")
        }, {}],
        30: [function (e, t, n) {
            (function (e) {
                function o(e) {
                    for (var t = 0; t < e.length; t++) {
                        var n = e[t];
                        if (n.buffer instanceof ArrayBuffer) {
                            var r = n.buffer;
                            if (n.byteLength !== r.byteLength) {
                                var i = new Uint8Array(n.byteLength);
                                i.set(new Uint8Array(r, n.byteOffset, n.byteLength)), r = i.buffer
                            }
                            e[t] = r
                        }
                    }
                }

                function u(e, t) {
                    t = t || {};
                    var r = new n;
                    o(e);
                    for (var i = 0; i < e.length; i++)r.append(e[i]);
                    return t.type ? r.getBlob(t.type) : r.getBlob()
                }

                function a(e, t) {
                    return o(e), new Blob(e, t || {})
                }

                var n = e.BlobBuilder || e.WebKitBlobBuilder || e.MSBlobBuilder || e.MozBlobBuilder, r = function () {
                    try {
                        var e = new Blob(["hi"]);
                        return e.size === 2
                    } catch (t) {
                        return !1
                    }
                }(), i = r && function () {
                        try {
                            var e = new Blob([new Uint8Array([1, 2])]);
                            return e.size === 2
                        } catch (t) {
                            return !1
                        }
                    }(), s = n && n.prototype.append && n.prototype.getBlob;
                t.exports = function () {
                    return r ? i ? e.Blob : a : s ? u : undefined
                }()
            }).call(this, typeof self != "undefined" ? self : typeof window != "undefined" ? window : {})
        }, {}],
        31: [function (t, n, r) {
            (function (t) {
                (function (i) {
                    function f(e) {
                        var t = [], n = 0, r = e.length, i, s;
                        while (n < r)i = e.charCodeAt(n++), i >= 55296 && i <= 56319 && n < r ? (s = e.charCodeAt(n++), (s & 64512) == 56320 ? t.push(((i & 1023) << 10) + (s & 1023) + 65536) : (t.push(i), n--)) : t.push(i);
                        return t
                    }

                    function l(e) {
                        var t = e.length, n = -1, r, i = "";
                        while (++n < t)r = e[n], r > 65535 && (r -= 65536, i += a(r >>> 10 & 1023 | 55296), r = 56320 | r & 1023), i += a(r);
                        return i
                    }

                    function c(e) {
                        if (e >= 55296 && e <= 57343)throw Error("Lone surrogate U+" + e.toString(16).toUpperCase() + " is not a scalar value")
                    }

                    function h(e, t) {
                        return a(e >> t & 63 | 128)
                    }

                    function p(e) {
                        if ((e & 4294967168) == 0)return a(e);
                        var t = "";
                        return (e & 4294965248) == 0 ? t = a(e >> 6 & 31 | 192) : (e & 4294901760) == 0 ? (c(e), t = a(e >> 12 & 15 | 224), t += h(e, 6)) : (e & 4292870144) == 0 && (t = a(e >> 18 & 7 | 240), t += h(e, 12), t += h(e, 6)), t += a(e & 63 | 128), t
                    }

                    function d(e) {
                        var t = f(e), n = t.length, r = -1, i, s = "";
                        while (++r < n)i = t[r], s += p(i);
                        return s
                    }

                    function v() {
                        if (b >= y)throw Error("Invalid byte index");
                        var e = g[b] & 255;
                        b++;
                        if ((e & 192) == 128)return e & 63;
                        throw Error("Invalid continuation byte")
                    }

                    function m() {
                        var e, t, n, r, i;
                        if (b > y)throw Error("Invalid byte index");
                        if (b == y)return !1;
                        e = g[b] & 255, b++;
                        if ((e & 128) == 0)return e;
                        if ((e & 224) == 192) {
                            var t = v();
                            i = (e & 31) << 6 | t;
                            if (i >= 128)return i;
                            throw Error("Invalid continuation byte")
                        }
                        if ((e & 240) == 224) {
                            t = v(), n = v(), i = (e & 15) << 12 | t << 6 | n;
                            if (i >= 2048)return c(i), i;
                            throw Error("Invalid continuation byte")
                        }
                        if ((e & 248) == 240) {
                            t = v(), n = v(), r = v(), i = (e & 15) << 18 | t << 12 | n << 6 | r;
                            if (i >= 65536 && i <= 1114111)return i
                        }
                        throw Error("Invalid UTF-8 detected")
                    }

                    function w(e) {
                        g = f(e), y = g.length, b = 0;
                        var t = [], n;
                        while ((n = m()) !== !1)t.push(n);
                        return l(t)
                    }

                    var s = typeof r == "object" && r, o = typeof n == "object" && n && n.exports == s && n,
                        u = typeof t == "object" && t;
                    if (u.global === u || u.window === u) i = u;
                    var a = String.fromCharCode, g, y, b, E = {version: "2.0.0", encode: d, decode: w};
                    if (typeof e == "function" && typeof e.amd == "object" && e.amd) e(function () {
                        return E
                    }); else if (s && !s.nodeType)if (o) o.exports = E; else {
                        var S = {}, x = S.hasOwnProperty;
                        for (var T in E)x.call(E, T) && (s[T] = E[T])
                    } else i.utf8 = E
                })(this)
            }).call(this, typeof self != "undefined" ? self : typeof window != "undefined" ? window : {})
        }, {}],
        32: [function (e, t, n) {
            (function (e) {
                var n = /^[\],:{}\s]*$/, r = /\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g,
                    i = /"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, s = /(?:^|:|,)(?:\s*\[)+/g,
                    o = /^\s+/, u = /\s+$/;
                t.exports = function (a) {
                    if ("string" != typeof a || !a)return null;
                    a = a.replace(o, "").replace(u, "");
                    if (e.JSON && JSON.parse)return JSON.parse(a);
                    if (n.test(a.replace(r, "@").replace(i, "]").replace(s, "")))return (new Function("return " + a))()
                }
            }).call(this, typeof self != "undefined" ? self : typeof window != "undefined" ? window : {})
        }, {}],
        33: [function (e, t, n) {
            n.encode = function (e) {
                var t = ""
                ;
                for (var n in e)e.hasOwnProperty(n) && (t.length && (t += "&"), t += encodeURIComponent(n) + "=" + encodeURIComponent(e[n]));
                return t
            }, n.decode = function (e) {
                var t = {}, n = e.split("&");
                for (var r = 0, i = n.length; r < i; r++) {
                    var s = n[r].split("=");
                    t[decodeURIComponent(s[0])] = decodeURIComponent(s[1])
                }
                return t
            }
        }, {}],
        34: [function (e, t, n) {
            var r = /^(?:(?![^:@]+:[^:@\/]*@)(http|https|ws|wss):\/\/)?((?:(([^:@]*)(?::([^:@]*))?)?@)?((?:[a-f0-9]{0,4}:){2,7}[a-f0-9]{0,4}|[^:\/?#]*)(?::(\d*))?)(((\/(?:[^?#](?![^?#\/]*\.[^?#\/.]+(?:[?#]|$)))*\/?)?([^?#\/]*))(?:\?([^#]*))?(?:#(.*))?)/,
                i = ["source", "protocol", "authority", "userInfo", "user", "password", "host", "port", "relative", "path", "directory", "file", "query", "anchor"];
            t.exports = function (t) {
                var n = t, s = t.indexOf("["), o = t.indexOf("]");
                s != -1 && o != -1 && (t = t.substring(0, s) + t.substring(s, o).replace(/:/g, ";") + t.substring(o, t.length));
                var u = r.exec(t || ""), a = {}, f = 14;
                while (f--)a[i[f]] = u[f] || "";
                return s != -1 && o != -1 && (a.source = n, a.host = a.host.substring(1, a.host.length - 1).replace(/;/g, ":"), a.authority = a.authority.replace("[", "").replace("]", "").replace(/;/g, ":"), a.ipv6uri = !0), a
            }
        }, {}],
        35: [function (e, t, n) {
            function s(e, t, n) {
                var r;
                return t ? r = new i(e, t) : r = new i(e), r
            }

            var r = function () {
                return this
            }(), i = r.WebSocket || r.MozWebSocket;
            t.exports = i ? s : null, i && (s.prototype = i.prototype)
        }, {}],
        36: [function (e, t, n) {
            (function (n) {
                function i(e) {
                    function t(e) {
                        if (!e)return !1;
                        if (n.Buffer && n.Buffer.isBuffer(e) || n.ArrayBuffer && e instanceof ArrayBuffer || n.Blob && e instanceof Blob || n.File && e instanceof File)return !0;
                        if (r(e)) {
                            for (var i = 0; i < e.length; i++)if (t(e[i]))return !0
                        } else if (e && "object" == typeof e) {
                            e.toJSON && (e = e.toJSON());
                            for (var s in e)if (Object.prototype.hasOwnProperty.call(e, s) && t(e[s]))return !0
                        }
                        return !1
                    }

                    return t(e)
                }

                var r = e("isarray");
                t.exports = i
            }).call(this, typeof self != "undefined" ? self : typeof window != "undefined" ? window : {})
        }, {isarray: 37}],
        37: [function (e, t, n) {
            t.exports = Array.isArray || function (e) {
                    return Object.prototype.toString.call(e) == "[object Array]"
                }
        }, {}],
        38: [function (e, t, n) {
            var r = e("global");
            try {
                t.exports = "XMLHttpRequest" in r && "withCredentials" in new r.XMLHttpRequest
            } catch (i) {
                t.exports = !1
            }
        }, {global: 39}],
        39: [function (e, t, n) {
            t.exports = function () {
                return this
            }()
        }, {}],
        40: [function (e, t, n) {
            var r = [].indexOf;
            t.exports = function (e, t) {
                if (r)return e.indexOf(t);
                for (var n = 0; n < e.length; ++n)if (e[n] === t)return n;
                return -1
            }
        }, {}],
        41: [function (e, t, n) {
            var r = Object.prototype.hasOwnProperty;
            n.keys = Object.keys || function (e) {
                    var t = [];
                    for (var n in e)r.call(e, n) && t.push(n);
                    return t
                }, n.values = function (e) {
                var t = [];
                for (var n in e)r.call(e, n) && t.push(e[n]);
                return t
            }, n.merge = function (e, t) {
                for (var n in t)r.call(t, n) && (e[n] = t[n]);
                return e
            }, n.length = function (e) {
                return n.keys(e).length
            }, n.isEmpty = function (e) {
                return 0 == n.length(e)
            }
        }, {}],
        42: [function (e, t, n) {
            var r = /^(?:(?![^:@]+:[^:@\/]*@)(http|https|ws|wss):\/\/)?((?:(([^:@]*)(?::([^:@]*))?)?@)?((?:[a-f0-9]{0,4}:){2,7}[a-f0-9]{0,4}|[^:\/?#]*)(?::(\d*))?)(((\/(?:[^?#](?![^?#\/]*\.[^?#\/.]+(?:[?#]|$)))*\/?)?([^?#\/]*))(?:\?([^#]*))?(?:#(.*))?)/,
                i = ["source", "protocol", "authority", "userInfo", "user", "password", "host", "port", "relative", "path", "directory", "file", "query", "anchor"];
            t.exports = function (t) {
                var n = r.exec(t || ""), s = {}, o = 14;
                while (o--)s[i[o]] = n[o] || "";
                return s
            }
        }, {}],
        43: [function (e, t, n) {
            (function (t) {
                var r = e("isarray"), i = e("./is-buffer");
                n.deconstructPacket = function (e) {
                    function s(e) {
                        if (!e)return e;
                        if (i(e)) {
                            var n = {_placeholder: !0, num: t.length};
                            return t.push(e), n
                        }
                        if (r(e)) {
                            var o = new Array(e.length);
                            for (var u = 0; u < e.length; u++)o[u] = s(e[u]);
                            return o
                        }
                        if ("object" != typeof e || e instanceof Date)return e;
                        var o = {};
                        for (var a in e)o[a] = s(e[a]);
                        return o
                    }

                    var t = [], n = e.data, o = e;
                    return o.data = s(n), o.attachments = t.length, {packet: o, buffers: t}
                }, n.reconstructPacket = function (e, t) {
                    function i(e) {
                        if (e && e._placeholder) {
                            var n = t[e.num];
                            return n
                        }
                        if (r(e)) {
                            for (var s = 0; s < e.length; s++)e[s] = i(e[s]);
                            return e
                        }
                        if (e && "object" == typeof e) {
                            for (var o in e)e[o] = i(e[o]);
                            return e
                        }
                        return e
                    }

                    var n = 0;
                    return e.data = i(e.data), e.attachments = undefined, e
                }, n.removeBlobs = function (e, n) {
                    function s(e, a, f) {
                        if (!e)return e;
                        if (t.Blob && e instanceof Blob || t.File && e instanceof File) {
                            o++;
                            var l = new FileReader;
                            l.onload = function () {
                                f ? f[a] = this.result : u = this.result, --o || n(u)
                            }, l.readAsArrayBuffer(e)
                        } else if (r(e))for (var c = 0; c < e.length; c++)s(e[c], c, e); else if (e && "object" == typeof e && !i(e))for (var h in e)s(e[h], h, e)
                    }

                    var o = 0, u = e;
                    s(u), o || n(u)
                }
            }).call(this, typeof self != "undefined" ? self : typeof window != "undefined" ? window : {})
        }, {"./is-buffer": 45, isarray: 46}],
        44: [function (e, t, n) {
            function f() {
            }

            function l(e) {
                var t = "", s = !1;
                t += e.type;
                if (n.BINARY_EVENT == e.type || n.BINARY_ACK == e.type) t += e.attachments, t += "-";
                return e.nsp && "/" != e.nsp && (s = !0, t += e.nsp), null != e.id && (s && (t += ",", s = !1), t += e.id), null != e.data && (s && (t += ","), t += i.stringify(e.data)), r("encoded %j as %s", e, t), t
            }

            function c(e, t) {
                function n(e) {
                    var n = u.deconstructPacket(e), r = l(n.packet), i = n.buffers;
                    i.unshift(r), t(i)
                }

                u.removeBlobs(e, n)
            }

            function h() {
                this.reconstructor = null
            }

            function p(e) {
                var t = {}, s = 0;
                t.type = Number(e.charAt(0));
                if (null == n.types[t.type])return v();
                if (n.BINARY_EVENT == t.type || n.BINARY_ACK == t.type) {
                    var o = "";
                    while (e.charAt(++s) != "-") {
                        o += e.charAt(s);
                        if (s == e.length)break
                    }
                    if (o != Number(o) || e.charAt(s) != "-")throw new Error("Illegal attachments");
                    t.attachments = Number(o)
                }
                if ("/" == e.charAt(s + 1)) {
                    t.nsp = "";
                    while (++s) {
                        var u = e.charAt(s);
                        if ("," == u)break;
                        t.nsp += u;
                        if (s == e.length)break
                    }
                } else t.nsp = "/";
                var a = e.charAt(s + 1);
                if ("" !== a && Number(a) == a) {
                    t.id = "";
                    while (++s) {
                        var u = e.charAt(s);
                        if (null == u || Number(u) != u) {
                            --s;
                            break
                        }
                        t.id += e.charAt(s);
                        if (s == e.length)break
                    }
                    t.id = Number(t.id)
                }
                if (e.charAt(++s))try {
                    t.data = i.parse(e.substr(s))
                } catch (f) {
                    return v()
                }
                return r("decoded %s as %j", e, t), t
            }

            function d(e) {
                this.reconPack = e, this.buffers = []
            }

            function v(e) {
                return {type: n.ERROR, data: "parser error"}
            }

            var r = e("debug")("socket.io-parser"), i = e("json3"), s = e("isarray"), o = e("component-emitter"),
                u = e("./binary"), a = e("./is-buffer");
            n.protocol = 4, n.types = ["CONNECT", "DISCONNECT", "EVENT", "BINARY_EVENT", "ACK", "BINARY_ACK", "ERROR"], n.CONNECT = 0, n.DISCONNECT = 1, n.EVENT = 2, n.ACK = 3, n.ERROR = 4, n.BINARY_EVENT = 5, n.BINARY_ACK = 6
                , n.Encoder = f, n.Decoder = h, f.prototype.encode = function (e, t) {
                r("encoding packet %j", e);
                if (n.BINARY_EVENT == e.type || n.BINARY_ACK == e.type) c(e, t); else {
                    var i = l(e);
                    t([i])
                }
            }, o(h.prototype), h.prototype.add = function (e) {
                var t;
                if ("string" == typeof e) t = p(e), n.BINARY_EVENT == t.type || n.BINARY_ACK == t.type ? (this.reconstructor = new d(t), this.reconstructor.reconPack.attachments === 0 && this.emit("decoded", t)) : this.emit("decoded", t); else {
                    if (!a(e) && !e.base64)throw new Error("Unknown type: " + e);
                    if (!this.reconstructor)throw new Error("got binary data when not reconstructing a packet");
                    t = this.reconstructor.takeBinaryData(e), t && (this.reconstructor = null, this.emit("decoded", t))
                }
            }, h.prototype.destroy = function () {
                this.reconstructor && this.reconstructor.finishedReconstruction()
            }, d.prototype.takeBinaryData = function (e) {
                this.buffers.push(e);
                if (this.buffers.length == this.reconPack.attachments) {
                    var t = u.reconstructPacket(this.reconPack, this.buffers);
                    return this.finishedReconstruction(), t
                }
                return null
            }, d.prototype.finishedReconstruction = function () {
                this.reconPack = null, this.buffers = []
            }
        }, {"./binary": 43, "./is-buffer": 45, "component-emitter": 9, debug: 10, isarray: 46, json3: 47}],
        45: [function (e, t, n) {
            (function (e) {
                function n(t) {
                    return e.Buffer && e.Buffer.isBuffer(t) || e.ArrayBuffer && t instanceof ArrayBuffer
                }

                t.exports = n
            }).call(this, typeof self != "undefined" ? self : typeof window != "undefined" ? window : {})
        }, {}],
        46: [function (e, t, n) {
            t.exports = e(37)
        }, {}],
        47: [function (t, n, r) {
            (function (t) {
                function h(e) {
                    if (h[e] !== o)return h[e];
                    var t;
                    if (e == "bug-string-char-index") t = "a"[0] != "a"; else if (e == "json") t = h("json-stringify") && h("json-parse"); else {
                        var r, i = '{"a":[1,true,false,null,"\\u0000\\b\\n\\f\\r\\t"]}';
                        if (e == "json-stringify") {
                            var s = f.stringify, u = typeof s == "function" && l;
                            if (u) {
                                (r = function () {
                                    return 1
                                }).toJSON = r;
                                try {
                                    u = s(0) === "0" && s(new Number) === "0" && s(new String) == '""' && s(n) === o && s(o) === o && s() === o && s(r) === "1" && s([r]) == "[1]" && s([o]) == "[null]" && s(null) == "null" && s([o, n, null]) == "[null,null,null]" && s({a: [r, true, false, null, "\0\b\n\f\r	"]}) == i && s(null, r) === "1" && s([1, 2], null, 1) == "[\n 1,\n 2\n]" && s(new Date(-864e13)) == '"-271821-04-20T00:00:00.000Z"' && s(new Date(864e13)) == '"+275760-09-13T00:00:00.000Z"' && s(new Date(-621987552e5)) == '"-000001-01-01T00:00:00.000Z"' && s(new Date(-1)) == '"1969-12-31T23:59:59.999Z"'
                                } catch (a) {
                                    u = !1
                                }
                            }
                            t = u
                        }
                        if (e == "json-parse") {
                            var c = f.parse;
                            if (typeof c == "function")try {
                                if (c("0") === 0 && !c(!1)) {
                                    r = c(i);
                                    var p = r["a"].length == 5 && r.a[0] === 1;
                                    if (p) {
                                        try {
                                            p = !c('"	"')
                                        } catch (a) {
                                        }
                                        if (p)try {
                                            p = c("01") !== 1
                                        } catch (a) {
                                        }
                                        if (p)try {
                                            p = c("1.") !== 1
                                        } catch (a) {
                                        }
                                    }
                                }
                            } catch (a) {
                                p = !1
                            }
                            t = p
                        }
                    }
                    return h[e] = !!t
                }

                var n = {}.toString, i, s, o, u = typeof e == "function" && e.amd, a = typeof JSON == "object" && JSON,
                    f = typeof r == "object" && r && !r.nodeType && r;
                f && a ? (f.stringify = a.stringify, f.parse = a.parse) : f = t.JSON = a || {};
                var l = new Date(-0xc782b5b800cec);
                try {
                    l = l.getUTCFullYear() == -109252 && l.getUTCMonth() === 0 && l.getUTCDate() === 1 && l.getUTCHours() == 10 && l.getUTCMinutes() == 37 && l.getUTCSeconds() == 6 && l.getUTCMilliseconds() == 708
                } catch (c) {
                }
                if (!h("json")) {
                    var p = "[object Function]", d = "[object Date]", v = "[object Number]", m = "[object String]",
                        g = "[object Array]", y = "[object Boolean]", b = h("bug-string-char-index");
                    if (!l)var w = Math.floor, E = [0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334],
                        S = function (e, t) {
                            return E[t] + 365 * (e - 1970) + w((e - 1969 + (t = +(t > 1))) / 4) - w((e - 1901 + t) / 100) + w((e - 1601 + t) / 400)
                        };
                    (i = {}.hasOwnProperty) || (i = function (e) {
                        var t = {}, r;
                        return (t.__proto__ = null, t.__proto__ = {toString: 1}, t).toString != n ? i = function (e) {
                            var t = this.__proto__, n = e in (this.__proto__ = null, this);
                            return this.__proto__ = t, n
                        } : (r = t.constructor, i = function (e) {
                            var t = (this.constructor || r).prototype;
                            return e in this && !(e in t && this[e] === t[e])
                        }), t = null, i.call(this, e)
                    });
                    var x = {"boolean": 1, number: 1, string: 1, "undefined": 1}, T = function (e, t) {
                        var n = typeof e[t];
                        return n == "object" ? !!e[t] : !x[n]
                    };
                    s = function (e, t) {
                        var r = 0, o, u, a;
                        (o = function () {
                            this.valueOf = 0
                        }).prototype.valueOf = 0, u = new o;
                        for (a in u)i.call(u, a) && r++;
                        return o = u = null, r ? r == 2 ? s = function (e, t) {
                            var r = {}, s = n.call(e) == p, o;
                            for (o in e)(!s || o != "prototype") && !i.call(r, o) && (r[o] = 1) && i.call(e, o) && t(o)
                        } : s = function (e, t) {
                            var r = n.call(e) == p, s, o;
                            for (s in e)(!r || s != "prototype") && i.call(e, s) && !(o = s === "constructor") && t(s);
                            (o || i.call(e, s = "constructor")) && t(s)
                        } : (u = ["valueOf", "toString", "toLocaleString", "propertyIsEnumerable", "isPrototypeOf", "hasOwnProperty", "constructor"], s = function (e, t) {
                            var r = n.call(e) == p, s, o,
                                a = !r && typeof e.constructor != "function" && T(e, "hasOwnProperty") ? e.hasOwnProperty : i;
                            for (s in e)(!r || s != "prototype") && a.call(e, s) && t(s);
                            for (o = u.length; s = u[--o]; a.call(e, s) && t(s));
                        }), s(e, t)
                    };
                    if (!h("json-stringify")) {
                        var N = {92: "\\\\", 34: '\\"', 8: "\\b", 12: "\\f", 10: "\\n", 13: "\\r", 9: "\\t"},
                            C = "000000", k = function (e, t) {
                                return (C + (t || 0)).slice(-e)
                            }, L = "\\u00", A = function (e) {
                                var t = '"', n = 0, r = e.length, i = r > 10 && b, s;
                                i && (s = e.split(""));
                                for (; n < r; n++) {
                                    var o = e.charCodeAt(n);
                                    switch (o) {
                                        case 8:
                                        case 9:
                                        case 10:
                                        case 12:
                                        case 13:
                                        case 34:
                                        case 92:
                                            t += N[o];
                                            break;
                                        default:
                                            if (o < 32) {
                                                t += L + k(2, o.toString(16));
                                                break
                                            }
                                            t += i ? s[n] : b ? e.charAt(n) : e[n]
                                    }
                                }
                                return t + '"'
                            }, O = function (e, t, r, u, a, f, l) {
                                var c, h, p, b, E, x, T, N, C, L, M, _, D, P, H, B;
                                try {
                                    c = t[e]
                                } catch (j) {
                                }
                                if (typeof c == "object" && c) {
                                    h = n.call(c);
                                    if (h == d && !i.call(c, "toJSON"))if (c > -1 / 0 && c < 1 / 0) {
                                        if (S) {
                                            E = w(c / 864e5);
                                            for (p = w(E / 365.2425) + 1970 - 1; S(p + 1, 0) <= E; p++);
                                            for (b = w((E - S(p, 0)) / 30.42); S(p, b + 1) <= E; b++);
                                            E = 1 + E - S(p, b), x = (c % 864e5 + 864e5) % 864e5, T = w(x / 36e5) % 24, N = w(x / 6e4) % 60, C = w(x / 1e3) % 60, L = x % 1e3
                                        } else p = c.getUTCFullYear(), b = c.getUTCMonth(), E = c.getUTCDate(), T = c.getUTCHours(), N = c.getUTCMinutes(), C = c.getUTCSeconds(), L = c.getUTCMilliseconds();
                                        c = (p <= 0 || p >= 1e4 ? (p < 0 ? "-" : "+") + k(6, p < 0 ? -p : p) : k(4, p)) + "-" + k(2, b + 1) + "-" + k(2, E) + "T" + k(2, T) + ":" + k(2, N) + ":" + k(2, C) + "." + k(3, L) + "Z"
                                    } else c = null; else typeof c.toJSON == "function" && (h != v && h != m && h != g || i.call(c, "toJSON")) && (c = c.toJSON(e))
                                }
                                r && (c = r.call(t, e, c));
                                if (c === null)return "null";
                                h = n.call(c);
                                if (h == y)return "" + c;
                                if (h == v)return c > -1 / 0 && c < 1 / 0 ? "" + c : "null";
                                if (h == m)return A("" + c);
                                if (typeof c == "object") {
                                    for (P = l.length; P--;)if (l[P] === c)throw TypeError();
                                    l.push(c), M = [], H = f, f += a;
                                    if (h == g) {
                                        for (D = 0, P = c.length; D < P; D++)_ = O(D, c, r, u, a, f, l), M.push(_ === o ? "null" : _);
                                        B = M.length ? a ? "[\n" + f + M.join(",\n" +
                                                f) + "\n" + H + "]" : "[" + M.join(",") + "]" : "[]"
                                    } else s(u || c, function (e) {
                                        var t = O(e, c, r, u, a, f, l);
                                        t !== o && M.push(A(e) + ":" + (a ? " " : "") + t)
                                    }), B = M.length ? a ? "{\n" + f + M.join(",\n" + f) + "\n" + H + "}" : "{" + M.join(",") + "}" : "{}";
                                    return l.pop(), B
                                }
                            };
                        f.stringify = function (e, t, r) {
                            var i, s, o, u;
                            if (typeof t == "function" || typeof t == "object" && t)if ((u = n.call(t)) == p) s = t; else if (u == g) {
                                o = {};
                                for (var a = 0, f = t.length,
                                         l; a < f; l = t[a++], (u = n.call(l), u == m || u == v) && (o[l] = 1));
                            }
                            if (r)if ((u = n.call(r)) == v) {
                                if ((r -= r % 1) > 0)for (i = "", r > 10 && (r = 10); i.length < r; i += " ");
                            } else u == m && (i = r.length <= 10 ? r : r.slice(0, 10));
                            return O("", (l = {}, l[""] = e, l), s, o, i, "", [])
                        }
                    }
                    if (!h("json-parse")) {
                        var M = String.fromCharCode,
                            _ = {92: "\\", 34: '"', 47: "/", 98: "\b", 116: "	", 110: "\n", 102: "\f", 114: "\r"}, D,
                            P, H = function () {
                                throw D = P = null, SyntaxError()
                            }, B = function () {
                                var e = P, t = e.length, n, r, i, s, o;
                                while (D < t) {
                                    o = e.charCodeAt(D);
                                    switch (o) {
                                        case 9:
                                        case 10:
                                        case 13:
                                        case 32:
                                            D++;
                                            break;
                                        case 123:
                                        case 125:
                                        case 91:
                                        case 93:
                                        case 58:
                                        case 44:
                                            return n = b ? e.charAt(D) : e[D], D++, n;
                                        case 34:
                                            for (n = "@", D++; D < t;) {
                                                o = e.charCodeAt(D);
                                                if (o < 32) H(); else if (o == 92) {
                                                    o = e.charCodeAt(++D);
                                                    switch (o) {
                                                        case 92:
                                                        case 34:
                                                        case 47:
                                                        case 98:
                                                        case 116:
                                                        case 110:
                                                        case 102:
                                                        case 114:
                                                            n += _[o], D++;
                                                            break;
                                                        case 117:
                                                            r = ++D;
                                                            for (i = D + 4; D < i; D++)o = e.charCodeAt(D), o >= 48 && o <= 57 || o >= 97 && o <= 102 || o >= 65 && o <= 70 || H();
                                                            n += M("0x" + e.slice(r, D));
                                                            break;
                                                        default:
                                                            H()
                                                    }
                                                } else {
                                                    if (o == 34)break;
                                                    o = e.charCodeAt(D), r = D;
                                                    while (o >= 32 && o != 92 && o != 34)o = e.charCodeAt(++D);
                                                    n += e.slice(r, D)
                                                }
                                            }
                                            if (e.charCodeAt(D) == 34)return D++, n;
                                            H();
                                        default:
                                            r = D, o == 45 && (s = !0, o = e.charCodeAt(++D));
                                            if (o >= 48 && o <= 57) {
                                                o == 48 && (o = e.charCodeAt(D + 1), o >= 48 && o <= 57) && H(), s = !1;
                                                for (; D < t && (o = e.charCodeAt(D), o >= 48 && o <= 57); D++);
                                                if (e.charCodeAt(D) == 46) {
                                                    i = ++D;
                                                    for (; i < t && (o = e.charCodeAt(i), o >= 48 && o <= 57); i++);
                                                    i == D && H(), D = i
                                                }
                                                o = e.charCodeAt(D);
                                                if (o == 101 || o == 69) {
                                                    o = e.charCodeAt(++D), (o == 43 || o == 45) && D++;
                                                    for (i = D; i < t && (o = e.charCodeAt(i), o >= 48 && o <= 57); i++);
                                                    i == D && H(), D = i
                                                }
                                                return +e.slice(r, D)
                                            }
                                            s && H();
                                            if (e.slice(D, D + 4) == "true")return D += 4, !0;
                                            if (e.slice(D, D + 5) == "false")return D += 5, !1;
                                            if (e.slice(D, D + 4) == "null")return D += 4, null;
                                            H()
                                    }
                                }
                                return "$"
                            }, j = function (e) {
                                var t, n;
                                e == "$" && H();
                                if (typeof e == "string") {
                                    if ((b ? e.charAt(0) : e[0]) == "@")return e.slice(1);
                                    if (e == "[") {
                                        t = [];
                                        for (; ; n || (n = !0)) {
                                            e = B();
                                            if (e == "]")break;
                                            n && (e == "," ? (e = B(), e == "]" && H()) : H()), e == "," && H(), t.push(j(e))
                                        }
                                        return t
                                    }
                                    if (e == "{") {
                                        t = {};
                                        for (; ; n || (n = !0)) {
                                            e = B();
                                            if (e == "}")break;
                                            n && (e == "," ? (e = B(), e == "}" && H()) : H()), (e == "," || typeof e != "string" || (b ? e.charAt(0) : e[0]) != "@" || B() != ":") && H(), t[e.slice(1)] = j(B())
                                        }
                                        return t
                                    }
                                    H()
                                }
                                return e
                            }, F = function (e, t, n) {
                                var r = I(e, t, n);
                                r === o ? delete e[t] : e[t] = r
                            }, I = function (e, t, r) {
                                var i = e[t], o;
                                if (typeof i == "object" && i)if (n.call(i) == g)for (o = i.length; o--;)F(i, o, r); else s(i, function (e) {
                                    F(i, e, r)
                                });
                                return r.call(e, t, i)
                            };
                        f.parse = function (e, t) {
                            var r, i;
                            return D = 0, P = "" + e, r = j(B()), B() != "$" && H(), D = P = null, t && n.call(t) == p ? I((i = {}, i[""] = r, i), "", t) : r
                        }
                    }
                }
                u && e(function () {
                    return f
                })
            })(this)
        }, {}],
        48: [function (e, t, n) {
            function r(e, t) {
                var n = [];
                t = t || 0;
                for (var r = t || 0; r < e.length; r++)n[r - t] = e[r];
                return n
            }

            t.exports = r
        }, {}]
    }, {}, [1])(1)
});
var Scratch = Scratch || {};
Scratch.Views = Scratch.Views || {}, Scratch.Views.CrossOriginInterface = Backbone.View.extend({
    defaults: {
        allowed_funcs: ["*"],
        allowed_origins: ["*"],
        target_domain: "*",
        context: window
    }, initialize: function () {
        this.options = _.extend({}, this.defaults, this.options), window.addEventListener("message", $.proxy(this.messageListener, this), !1)
    }, functionAllowed: function (e) {
        return this.options.allowed_funcs.indexOf(e) != -1 || this.options.allowed_funcs.indexOf("*") != -1
    }, originAllowed: function (e) {
        return this.options.allowed_origins.indexOf(e) != -1
    }, messageListener: function (e) {
        e.origin || (e = e.originalEvent);
        if (this.originAllowed(e.origin)) {
            var t = $.parseJSON(e.data);
            if (!this.functionAllowed(t.action))throw"Received action not allowed";
            this.receive(t.action, t.args, t.callback)
        }
    }, receive: function (e, t, n) {
        if (!e)return;
        var r = this.options.context[e], i = r.apply(this.options.context, t);
        this.post(n, [i], null)
    }, post: function (e, t, n) {
        if (!e)return;
        if (!this.functionAllowed(e))throw"Posted action not allowed";
        var r = JSON.stringify({action: e, args: t, callback: n});
        this.options.actor.postMessage(r, this.options.target_domain)
    }
}), Scratch.Models = Scratch.Models || {}, Scratch.Models.TipBar = Backbone.Model.extend({
    defaults: {
        currentTip: "", isOpen: !1, stackPos: 0, urlStack: [], tipsMap: {
            home: "home.html",
            hoc: "howto/hoc-intro.html",
            getStarted: "howto/get-started-intro.html",
            "madewithcode-card": "howto/cardtip-intro.html",
            "madewithcode-name": "howto/nametip-intro.html",
            bdaycard: "howto/bdaycard-intro.html",
            bearstack: "howto/bearstory-intro.html",
            bearstory: "howto/bearstory-intro.html",
            bearstory1: "howto/bearstory1.html",
            bird: "howto/bird-intro.html",
            card: "howto/bdaycard-intro.html",
            "catch": "howto/catch-intro.html",
            dance: "howto/dance-intro.html",
            dressup: "howto/fashion-intro.html",
            fashion: "howto/fashion-intro.html",
            favorite: "howto/favorite-intro.html",
            fly: "howto/fly-intro.html",
            hide: "howto/hide-intro.html",
            hide1: "howto/hide1.html",
            hide2: "howto/hide2.html",
            hoops: "howto/hoops-intro.html",
            hoops1: "howto/hoops1.html",
            makey: "howto/makey-intro.html",
            music: "howto/music-intro.html",
            name: "howto/nametip-intro.html",
            odetocode: "howto/odetocode-intro.html",
            codeweekeu: "howto/odetocode-intro.html",
            pet: "howto/vptip-intro.html",
            pong: "howto/pgame-intro.html",
            racegame: "howto/racegame-intro.html",
            soccer: "howto/soccer.html",
            story: "howto/story-intro.html",
            valentines: "howto/valentine-intro.html",
            scratchUI: "ui/editor-map.html",
            magicwand: "ui/magicwand.html",
            paint: "ui/image-editor-map.html",
            ext: "ui/extensions.html",
            ext1: "ext/ext1.html",
            ext2: "howto/wedo2setup-intro.html",
            wedosetup: "howto/wedo2setup-intro.html",
            wedo1: "ext/wedo1.html",
            wedo2: "ext/wedo2.html",
            "changeXposBy:": "blocks/change-x.html",
            "changeYposBy:": "blocks/change-y.html",
            heading: "blocks/direction.html",
            "glideSecs:toX:y:elapsed:from:": "blocks/glide.html",
            "gotoSpriteOrMouse:": "blocks/go-to.html",
            "gotoX:y:": "blocks/go-to-xy.html",
            bounceOffEdge: "blocks/if-on-edge-bounce.html",
            "forward:": "blocks/move-steps.html",
            "heading:": "blocks/point-direction.html",
            "pointTowards:": "blocks/point-towards.html",
            "say:": "blocks/say.html",
            "say:duration:elapsed:from:": "blocks/say-for-seconds.html",
            "turnLeft:": "blocks/turn-left.html",
            "turnRight:": "blocks/turn-right.html",
            "xpos:": "blocks/set-x.html",
            "ypos:": "blocks/set-y.html",
            "&": "blocks/and.html",
            "|": "blocks/or.html",
            "getAttribute:of:": "blocks/getattribute.html",
            "list:contains:": "blocks/list-contains.html",
            "computeFunction:of:": "blocks/computefunction.html",
            "*": "blocks/multiply.html",
            "+": "blocks/add.html",
            "-": "blocks/subtract.html",
            "/": "blocks/divide.html",
            "%": "blocks/mod.html",
            "<": "blocks/less-than.html",
            "=": "blocks/equal.html",
            ">": "blocks/greater-than.html",
            "append:toList:": "blocks/add-to-list.html",
            answer: "blocks/answer.html",
            doAsk: "blocks/ask-and-wait.html",
            backgroundIndex: "blocks/backdrop-number.html",
            sceneName: "blocks/backdrop-name.html",
            "broadcast:": "blocks/broadcast.html",
            doBroadcastAndWait: "blocks/broadcast-wait.html",
            "changeGraphicEffect:by:": "blocks/change-effect.html",
            "changeVar:by:": "blocks/change-variable.html",
            "changePenHueBy:": "blocks/changecolor.html",
            "changePenShadeBy:": "blocks/changeshadeby.html",
            "changePenSizeBy:": "blocks/changepensize.html",
            "changeSizeBy:": "blocks/change-size.html",
            "changeTempoBy:": "blocks/change-tempo.html",
            "changeVolumeBy:": "blocks/change-volume.html",
            clearPenTrails: "blocks/clear.html",
            filterReset: "blocks/clear-graphic-effects.html",
            "color:sees:": "blocks/color-is-touching.html",
            "contentsOfList:": "blocks/list.html",
            costumeIndex: "blocks/costume-number.html",
            createCloneOf: "blocks/create-clone.html",
            timeAndDate: "blocks/current-time.html",
            timestamp: "blocks/timestamp.html",
            "deleteLine:ofList:": "blocks/delete-from-list.html",
            deleteClone: "blocks/delete-this-clone.html",
            heading: "blocks/direction.html",
            "distanceTo:": "blocks/distance-to.html",
            doForever: "blocks/forever.html",
            "glideSecs:toX:y:elapsed:from:": "blocks/glide.html",
            "goBackByLayers:": "blocks/go-back-layer.html",
            "gotoSpriteOrMouse:": "blocks/go-to.html",
            comeToFront: "blocks/go-to-front.html",
            "gotoX:y:": "blocks/go-to-xy.html",
            hideblock: "blocks/hide.html",
            "hideList:": "blocks/hide-list.html",
            "hideVariable:": "blocks/hide-variable.html",
            doIf: "blocks/if.html",
            doIfElse: "blocks/if-else.html",
            bounceOffEdge: "blocks/if-on-edge-bounce.html",
            "insert:at:ofList:": "blocks/insert-list.html",
            "getLine:ofList:": "blocks/item-of-list.html",
            "concatenate:with:": "blocks/join.html",
            "keyPressed:": "blocks/key-pressed.html",
            "lineCountOfList:": "blocks/length-of-list.html",
            "stringLength:": "blocks/length-of.html",
            "letter:of:": "blocks/letter-of.html",
            soundLevel: "blocks/loudness.html",
            mousePressed: "blocks/mouse-down.html",
            mouseX: "blocks/mouse-x.html",
            mouseY: "blocks/mouse-y.html",
            "forward:": "blocks/move-steps.html",
            nextScene: "blocks/nextbackdrop.html",
            nextCostume: "blocks/next-costume.html",
            not: "blocks/not.html",
            putPenDown: "blocks/pendown.html",
            putPenUp: "blocks/penup.html",
            "randomFrom:to:": "blocks/pick-random.html",
            playDrum: "blocks/play_drum.html",
            "noteOn:duration:elapsed:from:": "blocks/playnote.html",
            "playSound:": "blocks/playsound.html",
            doPlaySoundAndWait: "blocks/playsound_untildone.html",
            "heading:": "blocks/point-direction.html",
            "pointTowards:": "blocks/point-towards.html",
            doRepeat: "blocks/repeat.html",
            doUntil: "blocks/repeat-until.html",
            "setLine:ofList:to:": "blocks/replace-list.html",
            timerReset: "blocks/reset-timer.html",
            readVariable: "blocks/variable.html",
            "rest:elapsed:from:": "blocks/rest.html",
            rounded: "blocks/round.html",
            "say:": "blocks/say.html",
            "say:duration:elapsed:from:": "blocks/say-for-seconds.html",
            "setGraphicEffect:to:": "blocks/set-effect.html",
            "setVar:to:": "blocks/set-variable.html",
            "instrument:": "blocks/setinstrument.html",
            "penColor:": "blocks/setpencolor.html",
            "setPenHueTo:": "blocks/setpencolorto.html",
            "setPenShadeTo:": "blocks/setshade.html",
            "penSize:": "blocks/setpensize.html",
            setRotationStyle: "blocks/setrotation.html",
            "setSizeTo:": "blocks/set-size.html",
            "setTempoTo:": "blocks/set-tempo.html",
            setVideoTransparency: "blocks/set-video-transparency.html",
            "setVolumeTo:": "blocks/set-volume.html",
            "xpos:": "blocks/set-x.html",
            "ypos:": "blocks/set-y.html",
            show: "blocks/show.html",
            "showList:": "blocks/show-list.html",
            "showVariable:": "blocks/show-variable.html",
            scale: "blocks/size.html",
            stampCostume: "blocks/stamp.html",
            stopScripts: "blocks/stop.html",
            stopAllSounds: "blocks/stopsound.html",
            startScene: "blocks/switch-backdrop.html",
            startSceneAndWait: "blocks/switch-backdrop-and-wait.html",
            "lookLike:": "blocks/switch-costume.html",
            tempo: "blocks/tempo.html",
            "think:": "blocks/think.html",
            "think:duration:elapsed:from:": "blocks/think-for-seconds.html",
            timer: "blocks/timer.html",
            "touching:": "blocks/touching.html",
            "touchingColor:": "blocks/touching-color.html",
            "turnLeft:": "blocks/turn-left.html",
            "turnRight:": "blocks/turn-right.html",
            setVideoState: "blocks/turn-video.html",
            getUserName: "blocks/username.html",
            senseVideoMotion: "blocks/video-motion.html",
            volume: "blocks/volume.html",
            "wait:elapsed:from:": "blocks/wait-secs.html",
            doWaitUntil: "blocks/wait-until.html",
            whenKeyPressed: "blocks/when-key-pressed.html",
            whenSensorGreaterThan: "blocks/when-loudness.html",
            whenGreenFlag: "blocks/when-flag-clicked.html",
            whenSceneStarts: "blocks/when-backdrop-switches.html",
            whenIReceive: "blocks/when-i-receive.html",
            whenCloned: "blocks/clone-startup.html",
            whenClicked: "blocks/when-this-sprite-clicked.html",
            xpos: "blocks/x-position.html",
            ypos: "blocks/y-position.html",
            procDef: "blocks/define.html",
            proc_declaration: "blocks/define.html",
            call: "blocks/usedefine.html",
            extensions: "ui/extensions.html",
            "ext:PicoBoard": "ext/PicoBoard.html",
            "ext:LEGO WeDo": "ext/wedo1.html",
            "ext:LEGO WeDo 2.0": "howto/wedo2setup-intro.html"
        }
    }
}), Scratch.Views = Scratch.Views || {}, Scratch.Views.TipBar = Backbone.View.extend({
    el: "#tip-bar",
    events: {
        "click .toggle-control": "toggle",
        "click #tip-bar-inner.tipsclosed": "toggle",
        "click .close-circle-dark": "toggle",
        "click .tip-home": "navToHome",
        "click a": "navByLink",
        "click .accordion-heading": "accordionSectionToggled"
    },
    template: _.template($("#template-tip-bar").html()),
    initialize: function () {
        function t(e) {
            return document.location.protocol + e.substr(e.indexOf(":") + 1)
        }

        this.originalWidth = this.$el.css("width");
        var e = this;
        this.a = this.model.attributes, this.setUrlPrefix(Scratch.INIT_DATA.TIPS.CURRENT_LANGUAGE), this.$w = $(window), window.tip_bar_api = {
            open: function (t) {
                e.open(t)
            }, close: function () {
                return e.close()
            }, fixIE: function () {
                e.fixIE()
            }, show: function () {
                e.show()
            }, hide: function () {
                e.$el.hide()
            }, toggle: function () {
                e.toggle()
            }, load: function (t) {
                return e.navTo(t)
            }, updateLanguage: function (t) {
                e.updateLanguage(t)
            }
        }, this.$el.html(this.template(this.a)), this.$tipContent = this.$tipContent || this.$el.find(".tip-content"), this.$tipHeader = this.$tipHeader || this.$el.find(".tip-header"), this.$tipInner = this.$tipInner || this.$el.find("#tip-bar-inner"), this.$tipContentContainer = this.$tipContentContainer || this.$tipContent.find("#tip-content-container"), this.crossOriginInterface = new Scratch.Views.CrossOriginInterface({
            actor: this.$tipContentContainer.get(0).contentWindow,
            context: e,
            allowed_origins: [t(Scratch.INIT_DATA.COI.TARGET_DOMAIN)],
            target_domain: t(Scratch.INIT_DATA.COI.TARGET_DOMAIN)
        });
        var n = $.urlParam("tip_bar"), r = $(window).width();
        this.trackingStarted = $.Deferred(), n && r >= 1e3 && (this.trackingStarted.resolve(), this.open(n, "trackPath")), window.scrollTo(window.pageXOffset, 0)
    },
    fixIE: function () {
        var e = window.pageYOffset;
        window.scrollTo(window.pageXOffset, 1 - e), window.scrollTo(window.pageXOffset, e)
    },
    toggle: function () {
        this.model.get("isOpen") ? (this.close(), _gaq.push(["_trackEvent", "project", "tip_bar_close"])) : (this.open(this.a.currentTip), _gaq.push(["_trackEvent", "project", "tip_bar_open"]))
    },
    open: function (e) {
        var t = this;
        this.trackingStarted.resolve();
        if (e) path = this.getPath(e), this.navTo(path); else if (!this.a.currentTip || this.a.currentTip.length == 0) path = this.getPath("home"), this.navTo(path);
        t.$tipInner.removeClass("tipsclosed").addClass("tipsopen"), t.$el.animate({width: "321px"}, function () {
            t.model.set({isOpen: !0})
        })
    },
    close: function () {
        var e = this, t = this.model.get("isOpen");
        return this.model.set({isOpen: !1}), this.$el.animate({width: this.originalWidth}, function () {
            e.$tipInner.removeClass("tipsopen").addClass("tipsclosed");
            try {
                e.crossOriginInterface.post("toggleVideos", [!1], null)
            } catch (t) {
            }
        }), t
    },
    show: function () {
        var e = this;
        return e.$el.show()
    },
    navByLink: function (e) {
        e.preventDefault(), $(e.currentTarget).parent().hasClass("accordion-heading") && (this.lastClickedSection = null);
        var t = e.currentTarget.pathname;
        t[0] !== "/" && (t = "/" + t), _gaq.push(["_trackEvent", "project", "tip_bar_close"]), this.navTo(t)
    },
    getPath: function (e) {
        var t = "";
        return e === this.a.currentTip ? e : e.indexOf(this.urlPrefix) === 0 ? e : (t = this.a.tipsMap[e] || e + ".html", this.urlPrefix + t)
    },
    navTo: function (e) {
        this.trackingStarted.done(function () {
            _gaq.push(["_trackPageview", "/tip-bar" + e])
        }), this.$tipContentContainer.attr("src", ""), this.$tipContentContainer.attr("src", e), this.render()
    },
    navToHome: function (e) {
        path = this.getPath("home"), this.navTo(path)
    },
    setModelPath: function (e) {
        this.a.urlStack = this.a.urlStack.slice(0, this.a.stackPos + 1), this.model.set({
            currentTip: e,
            stackPos: this.a.urlStack.push(e) - 1
        })
    },
    accordionSectionToggled: function (e) {
        if (e.target.href)return;
        this.$el.find(".expanded").removeClass("expanded").find("span").text("+");
        var t = $(e.currentTarget);
        this.lastClickedSection = t.attr("data-target"), $(this.lastClickedSection).hasClass("in") || t.addClass("expanded").find("span").text("-")
    },
    render: function () {
        var e = this;
        this.$tipContentContainer.on("load", function () {
            e.a.currentTip === e.a.tipsMap.home && e.lastClickedSection && window.setTimeout(function () {
                $(e.lastClickedSection).prev().trigger("click")
            }, 300)
        })
    },
    setUrlPrefix: function (e) {
        this.urlPrefix = Scratch.INIT_DATA.TIPS.HELP_URLS[e]
    },
    updateLanguage: function (e) {
        var t = "";
        this.a.currentTip.indexOf("/") == this.urlPrefix.indexOf("/") ? t = this.a.currentTip.substr(this.urlPrefix.length) : t = this.a.currentTip.substr(this.a.currentTip.indexOf(this.urlPrefix)).substr(this.urlPrefix.length), this.setUrlPrefix(e), t && this.navTo(this.urlPrefix + t)
    }
});
var Scratch = Scratch || {};
Scratch.Project = Scratch.Project || {};
var isCreatePage = document.location.pathname == "/projects/editor/";
Scratch.Project.Router = Backbone.Router.extend({
    routes: {
        "": isCreatePage ? "editor" : "player",
        editor: "editor",
        player: "player",
        fullscreen: "fullscreen",
        "comments*comment_id": "player"
    }, initialize: function () {
        swfobject.hasFlashPlayerVersion("1") || $("body").removeClass("black white"), this.projectModel = new Scratch.ProjectThumbnail(Scratch.INIT_DATA.PROJECT.model, {
            related: {
                lovers: new Scratch.UserThumbnailCollection([], {
                    model: Scratch.UserThumbnail,
                    collectionType: "lovers"
                }),
                favoriters: new Scratch.UserThumbnailCollection([], {
                    model: Scratch.UserThumbnail,
                    collectionType: "favoriters"
                }),
                tags: new Scratch.TagCollection([], {model: Scratch.Tag, collectionType: "project"}),
                comments: new Scratch.CommentCollection([], {model: Scratch.Comment, collectionType: "project"})
            }
        }), $.inArray("edit-project", Scratch.LoggedInUser.permissions) && (this.projectView || (this.projectView = new Scratch.Project.EditView({
            model: this.projectModel,
            el: $("#project")
        })));
        var e = this;
        $.when(window.SWFready).done(function () {
            Scratch.FlashApp = new Scratch.FlashAppView({
                model: e.projectModel,
                el: $("#scratch"),
                loggedInUser: Scratch.LoggedInUser,
                editor: !0,
                is_new: Scratch.INIT_DATA.PROJECT.is_new
            })
        }), Scratch.INIT_DATA.ADMIN && (this.adminView = new Scratch.AdminPanel({
            model: this.projectModel,
            el: $("#admin-panel")
        }))
    }, player: function (e) {
        if (Scratch.FlashApp) {
            var t = Scratch.FlashApp.beforeUnload();
            t && !confirm(t + "\n" + gettext("Would you like to leave the editor anyway?")) && (window.location.hash = "editor")
        }
        $.when(window.SWFready).done(function () {
            Scratch.FlashApp.setEditMode(!1)
        });
        var n = this;
        if (Scratch.INIT_DATA.PROJECT.model.isPublished || Scratch.INIT_DATA.ADMIN) this.commentView || (this.commentView = new Scratch.Comments({
            el: $("#comments"),
            scrollTo: e,
            type: "project",
            typeId: Scratch.INIT_DATA.PROJECT.model.id
        }))
    }, editor: function () {
        $.when(window.SWFready).done(function () {
            Scratch.FlashApp.setEditMode(!0)
        })
    }, fullscreen: function () {
        $("body").removeClass("editor").addClass("editor"), $.when(window.SWFready).done(function () {
            Scratch.FlashApp.ASobj.ASsetPresentationMode(!0)
        })
    }
}), $(function () {
    app = new Scratch.Project.Router
        , Backbone.history.start()
});
var Scratch = Scratch || {};
Scratch.FlashAppView = Backbone.View.extend({
    initialize: function (e) {
        this.loggedInUser = Scratch.LoggedInUser, this.isEditMode = Scratch.INIT_DATA.PROJECT.is_new, this.ASobj = swfobject.getObjectById(this.$el.attr("id")), this.setContextMenuHandler(), this.model.id != null ? this.loadProjectInSwf() : this.ASobj.AScreateProject(this.loggedInUser.get("username")), this.setLoggedInUser();
        if (this.options.editor) {
            _.bindAll(this, "beforeUnload", "setLoggedInUser");
            try {
                new Scratch.Views.TipBar({model: new Scratch.Models.TipBar})
            } catch (t) {
                console.log("Tip bar failed to load.  Check code for errors")
            }
            this.model.bind("change:title", this.setTitle, this), this.loggedInUser.bind("change", this.setLoggedInUser, this), $(window).on("beforeunload", this.beforeUnload), $("#new-scratch-project .button").on("click", this.ASobj.ASdownload)
        }
    }, setLoggedInUser: function () {
        this.ASobj.ASsetLoginUser(this.loggedInUser.get("username"), this.lastEditorOp), this.lastEditorOp = ""
    }, setContextMenuHandler: function () {
        function e(e) {
            return e.pageY > 24 && (e.which > 1 || e.ctrlKey)
        }

        var t = this;
        t.el.addEventListener ? t.el.parentNode.addEventListener("mousedown", function (n) {
            var r = $.Event("mousedown", n);
            e(r) && (n.stopPropagation(), n.preventDefault(), t.customContextMenu(r))
        }, !0) : t.$el.parent().on("mousedown", function (t) {
            e(t) && this.setCapture()
        }).on("mouseup", function (n) {
            e(n) && (t.customContextMenu(n), this.releaseCapture())
        }).on("contextmenu", function (e) {
            e.preventDefault()
        })
    }, customContextMenu: function (e) {
        if (!this.ASobj.ASisEditMode())return;
        var t = $(this.ASobj).offset(), n = (e.screenX - (window.screenX || window.screenLeft || -5)) / e.pageX,
            r = n * (e.pageX - t.left), i = n * (e.pageY - t.top), s = navigator.userAgent.indexOf("Macintosh") > -1,
            o = navigator.userAgent.indexOf("Chrome") == -1;
        this.ASobj.ASrightMouseDown(r, i, s && o)
    }, setEditMode: function (e) {
        this.isEditMode = e, this.ASobj.ASsetEditMode(e);
        if (e) {
            $("body").scrollTop(0), this.loggedInUser.authenticated && this.model.id != null && $.get("/log/project-see-inside/" + this.model.id + "/"), $("body").removeClass("editor").addClass("editor black");
            var t = navigator.userAgent.indexOf("Safari") > -1, n = navigator.userAgent.indexOf("Chrome") > -1,
                r = navigator.userAgent.indexOf("Version/5") > -1;
            n && t && (t = !1), !t || !r ? $("body #pagewrapper").animate({opacity: 1}, 1e3, function () {
                $("body").removeClass("black white"), $("body #pagewrapper").css("opacity", "1")
            }) : ($("body #pagewrapper").css("opacity", 1), $("body").removeClass("white black"));
            try {
                tip_bar_api.show()
            } catch (i) {
                console.log("Tip bar failed to load.  Check code for errors")
            }
        } else if (Scratch.FlashApp.model.id == Scratch.INIT_DATA.PROJECT.model["id"]) {
            $("body").removeClass("editor white").addClass("viewer"), this.ASobj.ASwasEdited() && this.model.save({datetime_modified: Date.now()});
            try {
                tip_bar_api.hide()
            } catch (i) {
                console.log("Tip bar failed to load.  Check code for errors")
            }
        } else JSredirectTo(Scratch.FlashApp.model.id, !1, {title: Scratch.FlashApp.model.get("title")})
    }, beEmbedded: function () {
        this.ASobj.ASsetEditMode(!0)
    }, setTitle: function () {
        this.model.save({visibility: "visible"}), this.ASobj.ASsetTitle(this.model.get("title"))
    }, loadProjectInSwf: function () {
        this.ASobj.ASloadProject(this.model.get("creator"), this.model.id, this.model.get("title"), !this.model.get("isPublished"), !1)
    }, beforeUnload: function (e) {
        if (!this.isEditMode && !e)return;
        if (!this.loggedInUser.authenticated) {
            if (this.ASobj.ASisUnchanged())return;
            return gettext("Your changes are NOT SAVED!\nTo save, stay on this page, then log in.")
        }
        if (this.model.get("creator") != this.loggedInUser.get("username")) {
            if (this.ASobj.ASisUnchanged())return;
            return this.isEditMode ? gettext("Your changes are NOT SAVED!\nTo save, stay on this page, then click \u201cRemix\u201d.") : gettext("Your changes are NOT SAVED!\nTo save, stay on this page, click \u201cSee inside\u201d, then click \u201cRemix\u201d.")
        }
        this.ASobj.ASwasEdited() && this.model.save({datetime_modified: Date()}, {async: !1});
        var t = this.model.get("title").indexOf("Untitled") == 0;
        if (t && !this.model.get("isPublished") && this.ASobj.ASisEditMode() && this.ASobj.ASisEmpty()) {
            this.model.save({visibility: "trshbyusr"}, {async: !1});
            return
        }
        if (this.ASobj.ASisUnchanged())return;
        return gettext("Your changes are NOT SAVED!\nTo save, stay on this page, then click \u201cSave now.\u201d")
    }, syncSaveProject: function () {
        if (!this.ASobj.ASshouldSave())return;
        var e = this.ASobj.ASgetProject();
        if (e == null || e.length == 0)return;
        $.ajax({url: "/internalapi/project/" + this.model.get("id") + "/set/", type: "POST", async: !1, data: e})
    }, sendReport: function (e, t) {
        t.thumbnail = this.ASobj.ASdumpRecordThumbnail();
        var n = JSON.stringify(t);
        $.ajax({
            url: e, type: "POST", data: n, success: function (e) {
                _gaq.push(["_trackEvent", "project", "report_add"]), e.moderation_status === "notreviewed" && window.location.reload()
            }, contentType: "application/json", error: function (e) {
                return e.errorThrown
            }
        })
    }
});
var notification = null, Scratch = Scratch || {};
Scratch.Project = Scratch.Project || {}, Scratch.Project.EditableTextField = Scratch.EditableTextField.extend({
    error: function (e, t, n) {
        this.$el.removeClass("loading"), Scratch.INIT_DATA.IS_IP_BANNED && $("#ip-mute-ban").modal()
    }
}), Scratch.Project.EditTitle = Scratch.Project.EditableTextField.extend({
    initialize: function (e, t) {
        _.bindAll(this, "success", "error", "saveEditable"), this.$eField = this.$("textarea"), this.eField = this.$eField[0], this.model.bind("change:title", this.render, this), Scratch.EditableTextField.prototype.initialize.apply(this, [t]), this.$("input").length && this.$("input").limit("52")
    }, render: function () {
        this.$("input").val(this.model.get("title"))
    }
}), Scratch.Project.EditNotes = Scratch.Project.EditableTextField.extend({
    initialize: function (e, t) {
        _.bindAll(this, "success", "error", "saveEditable"), this.$eField = this.$("textarea"), this.eField = this.$eField[0], this.$eField.on("keyup change", function () {
            self.$("textarea").val().length >= 5e3 && Scratch.AlertView.msg($("#alert-view"), {
                alert: "error",
                msg: Scratch.ALERT_MSGS["editable-text-too-long"]
            })
        }).limit("5000")
    }, onEditSuccess: function (e) {
        Scratch.AlertView.msg($("#alert-view"), {
            alert: "success", msg: Scratch.ALERT_MSGS["notes-changed"
                ]
        }), this.$eField.val().replace(/^\s+|\s+$/g, "") !== "" ? this.$el.parents(".tooltip").removeClass("force") : this.$el.parents(".tooltip").addClass("force")
    }
}), Scratch.Project.MarkDraft = Backbone.View.extend({
    events: {'click [type="checkbox"]': "markAsDraft"},
    markAsDraft: function (e) {
        $(e.target).is(":checked") ? this.model.related.tags.addItems("work-in-progress") : this.model.related.tags.removeItems("work-in-progress"), $(e.target).parent("#wip").toggleClass("on")
    }
}), Scratch.Project.AddTags = Scratch.EditableTextField.extend({
    template: _.template('<span class="tag"><a href=""><%= tag %></a> <span data-tag-name="<%= tag %>" data-control="remove">x</span></span>'),
    events: {
        'click [data-control="remove"]': "removeTag",
        click: "showTagDropdown",
        "keydown input.tag-input": "getKeyPress",
        'click [data-control="add-tag"]': "addCategory"
    },
    initialize: function (e, t) {
        _.bindAll(this, "hideTagDropDown", "submitTag", "limitTags", "success", "error", "saveEditable"), this.$eField = this.$("input.tag-input"), this.eField = this.$eField[0], $("body").on("click", this.hideTagDropDown), this.adjustInputWidth()
    },
    adjustInputWidth: function () {
        if (this.$('[data-content="tag-list"]').width() > 350)if (this.$('[data-content="tag-list"]').height() > 45) {
            var e = $('<div data-content="tag-list-full">'), t = 0;
            this.$("span.tag").each(function (n, r) {
                t += $(r).width();
                if (t >= 250)return !1;
                e.append($(r))
            }), this.$('[data-content="tag-list"]').before(e)
        } else this.$('[data-content="tag-list"]').width() > 300 && this.$('[data-content="tag-list"]').after('<div data-content="tag-list">').removeAttr("data-content"); else this.$('[data-cotent="tag-list"]').remove(), this.$('[data-content="tag-list-full"]').removeAttr("data-content").attr("data-content", "tag-list")
    },
    getKeyPress: function (e) {
        if (this.limitTags(e))return !1;
        if (e.which == 32 || e.which == 188 || e.which == 13 || e.which == 9) this.addTag(), e.preventDefault()
    },
    addTag: function () {
        var e = this.$('input[name="tags"]').val();
        e = e.replace(/\s+/g, "").replace(/,/g, ""), this.$('input[name="tags"]').val(""), e && this.submitTag(e)
    },
    addCategory: function (e) {
        this.submitTag($(e.target).data("tag"))
    },
    submitTag: function (e) {
        var t = this;
        if (!e)throw"tag is empty in submitTag.  This should never happen.";
        is_tag_valid(e) ? e.length > 100 ? Scratch.AlertView.msg($("#alert-view"), {
            alert: "error",
            msg: Scratch.ALERT_MSGS["editable-text-too-long"]
        }) : this.model.addItems(e, {
            success: function (n, r) {
                if (r && r[0] && r[0].isBad)return Scratch.AlertView.msg($("#alert-view"), {
                    alert: "error",
                    timer: 2e4,
                    msg: Scratch.ALERT_MSGS["inappropriate-tag"]
                });
                t.$('[data-content="tag-list"]').append(t.template({tag: e})), t.adjustInputWidth(), t.$el.removeClass("no-tags").addClass("has-tags"), t.$(".tag-box").removeClass("editable-empty").addClass("editable"), Scratch.AlertView.msg($("#alert-view"), {
                    alert: "success",
                    msg: Scratch.ALERT_MSGS["tags-changed"]
                }), t.hideTagDropDown()
            }
        }) : Scratch.AlertView.msg($("#alert-view"), {
            alert: "error",
            msg: 'Invalid tag "' + _.escape(e) + '": tags cannot contain punctuation.'
        })
    },
    limitTags: function (e) {
        if (this.$(".tag").length > 2)return this.$(".tag-box").stop().css("backgroundColor", "#FCC").animate({backgroundColor: "#FFF"}, "slow"), this.$(".tag-input").blur(), Scratch.AlertView.msg($("#alert-view"), {
            alert: "error",
            msg: Scratch.ALERT_MSGS["tags-limited"]
        }), !0
    },
    showTagDropdown: function (e) {
        if (this.limitTags())return;
        var t = this;
        this.$(".tag-box").removeClass("read").addClass("write"), setTimeout(function () {
            t.$(".tag-choices").show(), t.clearPrompt()
        }, 0)
    },
    hideTagDropDown: function (e) {
        this.$(".tag-choices").hide()
    },
    removeTag: function (e) {
        var t = this;
        e.stopPropagation(), this.model.removeItems($(e.target).data("tag-name"), {
            success: function () {
                $(e.target).parent(".tag").remove(), t.adjustInputWidth(), t.$("div .tag").length || (t.$el.removeClass("has-tags").addClass("no-tags"), t.$(".tag-box").removeClass("editable").addClass("editable-empty")), Scratch.AlertView.msg($("#alert-view"), {
                    alert: "success",
                    msg: Scratch.ALERT_MSGS["tags-changed"]
                })
            }
        })
    }
}), Scratch.Project.UpdateStats = Backbone.View.extend({
    events: {'click [data-control="toggle-stat"]': "toggleStat"},
    toggleStat: function (e) {
        var t = $(e.currentTarget), n = parseInt(t.find(".icon").html()), r = t.data("add"),
            i = t.data("stat") == "favoriters" ? "favorite" : t.data("stat") == "lovers" ? "love" : "unknown_stat";
        r ? (_gaq.push(["_trackEvent", "project", i + "_add"]), this.model.related[t.data("stat")].addItems(Scratch.LoggedInUser.get("username")), t.data("add", !1), t.find(".icon").addClass("on").html(n + 1)) : (_gaq.push(["_trackEvent", "project", i + "_remove"]), this.model.related[t.data("stat")].removeItems(Scratch.LoggedInUser.get("username")), t.data("add", !0), t.find(".icon").removeClass("on").html(n - 1))
    }
}), Scratch.Project.ShareBar = Backbone.View.extend({
    initialize: function () {
        this.model.bind("change:isPublished", this.success, this), _.bindAll(this, "success")
    }, events: {"submit .share-form": "preShareProject"}, preShareProject: function (e) {
        var t = this;
        e.preventDefault();
        if (Scratch.INIT_DATA.IS_IP_BANNED) {
            $("#ip-mute-ban").modal();
            return
        }
        if (Scratch.INIT_DATA.PROJECT.is_permcensored) {
            var n = _.template($("#template-permacensor-reshare-dialog").html());
            $(n()).dialog({
                title: "Cannot Re-Share Project", buttons: {
                    Ok: function () {
                        $(this).dialog("close")
                    }
                }
            });
            return
        }
        if (!Scratch.INIT_DATA.IS_SOCIAL) {
            openResendDialogue();
            return
        }
        if (Scratch.INIT_DATA.PROJECT.is_censored) {
            var n = _.template($("#template-censor-reshare-dialog").html());
            $(n()).dialog({
                title: "Share Project", buttons: {
                    "Yes, I think it's ok for Scratch now.": function () {
                        $(this).dialog("close"), t.shareProject()
                    }, Cancel: function () {
                        $(this).dialog("close")
                    }
                }
            })
        } else t.shareProject()
    }, shareProject: function () {
        if (Scratch.FlashApp.ASobj.AScanShare()) $.ajax({
            type: "POST",
            url: this.model.url() + "share/",
            success: function () {
                this.model.set({isPublished: !0}), window.location.href = "/projects/" + Scratch.FlashApp.model.get("id") + "/"
            }.bind(this),
            error: function (e) {
                return e.errorThrown
            }
        }); else {
            var e = this;
            e.$el.fadeOut("fast", function () {
                e.$(".public").text("Project cannot be shared because it uses an experimental extension."), e.$el.fadeIn("fast")
            })
        }
    }, success: function () {
        var e = this;
        e.$el.fadeOut("fast", function () {
            e.$(".public").text("Project shared. Reloading to display shared actions."), e.$el.fadeIn("fast")
        }), _.delay(function () {
            location.reload()
        }, 5e3)
    }
}), Scratch.Project.Download = Backbone.View.extend({
    events: {"click .close": "close"}, open: function () {
        $(".player-box-footer-module").hide(), this.$el.show().animate({height: "100"})
    }, close: function () {
        this.$el.animate({height: "0"}, function () {
            $(this).hide()
        })
    }, toggleOpen: function () {
        this.isOpen && this.$el.css("height") > 0 ? this.close() : this.open()
    }
}), Scratch.Project.AddTo = Backbone.View.extend({
    events: {
        "click .checkmark": "addProject",
        "click .checkmark-checked": "removeProject",
        "click .next-page": "loadMore",
        "click .close": "close"
    }, initialize: function (e) {
        _.bindAll(this, "open"), this.firstLoadComplete = !1, this.isOpen = !1, this.projectId = e.project
    }, loadMore: function () {
        var e = this.$el.find(".next-page").data("url") || null, t = this;
        e != null && $.ajax({url: e + "?project_id=" + this.projectId}).always(function (e) {
            t.$el.find(".next-page").remove()
        }).done(function (e) {
            t.$el.find("ul").append(e)
        })
    }, addProject: function (e) {
        var t = $(e.target).parent("li").data("studio-id");
        $(e.target).addClass("loading"), $.ajax({
            url: "/site-api/projects/in/" + t + "/add/?pks=" + this.projectId,
            type: "PUT"
        }).done(function (t) {
            $(e.target).removeClass("checkmark").addClass("checkmark-checked")
        }).always(function (t) {
            $(e.target).removeClass("loading")
        })
    }, removeProject: function (e) {
        var t = $(e.target).parents("li").data("studio-id");
        $(e.target).addClass("loading"), $.ajax({
            url: "/site-api/projects/in/" + t + "/remove/?pks=" + this.projectId,
            type: "PUT"
        }).done(function (t) {
            $(e.target).removeClass("checkmark-checked").addClass("checkmark")
        }).always(function (t) {
            $(e.target).removeClass("loading")
        })
    }, close: function () {
        this.$el.animate({height: "0"}, function () {
            $(this).hide()
        })
    }, open: function () {
        $(".player-box-footer-module").hide(), this.firstLoadComplete || this.loadMore(), this.$el.show().animate({height: "250"})
    }, toggleOpen: function () {
        this.isOpen && this.$el.attr("data-type") == "share" && this.$el.css("height") > 0 ? this.close() : this.open()
    }
}), Scratch.Project.ShareTo = Backbone.View.extend({
    events: {"click .close": "close"}, initialize: function (e) {
        _.bindAll(this, "open"), this.isOpen = !1, this.embedUrl = e.embedUrl
    }, close: function () {
        this.$el.animate({height: "0"}, function () {
            $(this).hide()
        })
    }, open: function () {
        $(".player-box-footer-module").hide(), this.$el.show().animate({height: "250"})
    }, toggleOpen: function () {
        this.isOpen && this.$el.attr("data-type") == "share" && this.$el.css("height") > 0 ? this.close() : this.open()
    }, setSize: function (e) {
        var t = $(e.target).val();
        t == "small" ? (this.width = 302, this.height = 252) : t == "medium" ? (this.width = 402, this.height = 355) : t == "large" && (this.width = 602, this.height = 502), this.$("textarea").val(this.embedCode())
    }, embedCode: function () {
        return '<iframe allowtransparency="true" width="' + this.width + '" height="' + this.height + '" src="' + this.embedUrl + "?auto_start=" + this.autoStart + '" frameborder="0"></iframe>'
    }
}), Scratch.Project.Report = Backbone.View.extend({
    template: _.template($("#template-report").html()),
    initialize: function (e) {
        this.isOpen = !1, this.isSent = !1
    },
    events: {
        'click [data-control="close"]': "close",
        'click [data-control="submit"]': "submit",
        "change #report-category-selector": "changeReportText"
    },
    render: function () {
        this.$el.html(this.template()), this.$el.attr("data-type", "report"), this.isSent && (this.$("div.form").hide(), this.$("div.message").show())
    },
    changeReportText: function (e) {
        var t = this.$("select#report-category-selector").val();
        this.$("#report-explanation").attr("placeholder", Scratch.INIT_DATA.PROJECT.reportText[t])
    },
    close: function () {
        this.$el.animate({height: "0"}, function () {
            $(this).hide()
        }), this.isOpen = !1
    },
    open: function () {
        $(".player-box-footer-module").hide();
        if (this.$el.css("height") > 0) {
            var e = this;
            this.$el.show().animate({
                height: "0", complete: function () {
                    e.open()
                }
            }), this.isOpen = !1
        } else this.render(), this.$el.show().animate({height: "250"}), this.isOpen = !0
    },
    toggleOpen: function () {
        this.isOpen && this.$el.attr("data-type") == "report" && this.$el.css("height") > 0 ? this.close() : this.open()
    },
    isValidReport: function (e, t) {
        var n = !0, r = "";
        return t.length === 0 ? (n = !1, r = Scratch.ALERT_MSGS["project-complaint-type"]) : e.length < 20 && (n = !1, r = Scratch.ALERT_MSGS["project-complaint-length"]), [n, r]
    },
    submit: function () {
        var e = this.$("textarea").val(), t = this.$("#report-category-selector").val(), n = this.isValidReport(e, t);
        if (!n[0]) {
            Scratch.AlertView.msg($("#alert-view"), {alert: "error", msg: n[1]});
            return
        }
        if (this.$el.data("report-mode") != "moderate" && !confirm(gettext("Are you sure this project is disrespectful or inappropriate, and breaks the community guidelines? If not, click cancel, and then click the community guidelines link at the bottom of the page to learn more.")))return;
        /\w+/.test(e) ? ($.when(window.SWFready).done(function () {
            Scratch.FlashApp.sendReport(this.model.url() + "report/", {notes: e, report_category: t})
        }.bind(this)), this.$("div.form").hide(), this.$("div.message").show(), this.isSent = !0) : this.$("textarea").val("")
    }
}), Scratch.Project.EditView = Backbone.View.extend({
    initialize: function () {
        this.title = new Scratch.Project.EditTitle({
            el: this.$("#title"),
            model: this.model
        }), this.notes = new Scratch.Project.EditNotes({
            el: this.$("#description"),
            model: this.model
        }), this.instructions = new Scratch.Project.EditNotes({
            el: this.$("#instructions"),
            model: this.model
        }), this.isDraft = new Scratch.Project.MarkDraft({
            el: this.$("#wip"),
            model: this.model
        }), this.stats = new Scratch.Project.UpdateStats({
            el: this.$("#stats"),
            model: this.model
        }), this.share = new Scratch.Project.ShareBar({
            el: $("#share-bar"),
            model: this.model
        }), this.addTo = new Scratch.Project.AddTo({
            el: $("#add-to-menu"),
            project: Scratch.INIT_DATA.PROJECT.model.id
        }), this.addTags = new Scratch.Project.AddTags({
            el: $("#project-tags"),
            model: this.model.related.tags
        }), this.shareTo = new Scratch.Project.ShareTo({
            el: $("#share-to-menu"),
            embedUrl: Scratch.INIT_DATA.PROJECT.embedUrl
        }), this.report = new Scratch.Project.Report({
            el: $("#player-box-footer"),
            model: this.model
        }), this.download = new Scratch.Project.Download({el: $("#download-menu"), model: this.model});
        var e = 0;
        setHeight = function () {
            var t = $("#instructions"
            ).parent().height(), n = $("#instructions").height(), r = t - n;
            if (r < 0) {
                e++ < 100 && setTimeout(setHeight, 10);
                return
            }
            var i = $("#info").height() - $("#fixed").height();
            $("#instructions, #description").height((i - 2 * r) / 2 - 5), $("#instructions").length || $("#description").height(i - r - 10), $("#description .viewport").length && $("#instructions, #description").tinyscrollbar()
        }, setHeight()
    },
    events: {
        "click #share-to": "openShareTo",
        "click #report-this": "openReport",
        "click #add-to": "openAddTo",
        "click #download": "openDownload"
    },
    render: function () {
    },
    openShareTo: function () {
        this.shareTo.toggleOpen()
    },
    openReport: function () {
        this.report.toggleOpen()
    },
    openAddTo: function () {
        this.addTo.toggleOpen()
    },
    openDownload: function () {
        this.download.toggleOpen()
    }
}), Scratch.Tag = Scratch.Model.extend({
    urlRoot: "/site-api/tags/all/",
    slug: "name"
}), Scratch.TagCollection = Scratch.Collection.extend({
    model: Scratch.Tag,
    urlRoot: "/site-api/tags/",
    slug: "name",
    initialize: function (e, t) {
        this.options = t
    }
}), window.ScratchProxies = new function () {
    function n(e) {
        var n = new Uint8Array(e), r, i = n.length, s = "";
        for (r = 0; r < i; r += 3)s += t[n[r] >> 2], s += t[(n[r] & 3) << 4 | n[r + 1] >> 4], s += t[(n[r + 1] & 15) << 2 | n[r + 2] >> 6], s += t[n[r + 2] & 63];
        return i % 3 === 2 ? s = s.substring(0, s.length - 1) + "=" : i % 3 === 1 && (s = s.substring(0, s.length - 2) + "=="), s
    }

    function r(e) {
        var n = e.length * .75, r = e.length, i, s = 0, o, u, a, f;
        e[e.length - 1] === "=" && (--n, e[e.length - 2] === "=" && --n);
        var l = new ArrayBuffer(n), c = new Uint8Array(l);
        for (i = 0; i < r; i += 4)o = t.indexOf(e[i]), u = t.indexOf(e[i + 1]), a = t.indexOf(e[i + 2]), f = t.indexOf(e[i + 3]), c[s++] = o << 2 | u >> 4, c[s++] = (u & 15) << 4 | a >> 2, c[s++] = (a & 3) << 6 | f & 63;
        return l
    }

    var e = this, t = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    e.AddHidProxies = function (e) {
        e.write = function (t, r) {
            var i = n(t);
            e.write_raw(i, r)
        }, e.send_feature_report = function (t, r) {
            var i = n(t);
            e.send_feature_report_raw(i, r)
        }, e.read = function (t, n) {
            e.read_raw(t, function (e) {
                n && (e = r(e), n(e))
            })
        }, e.get_feature_report = function (t, n) {
            e.get_feature_report_raw(t, function (e) {
                n && (e = r(e), n(e))
            })
        }
    }, e.AddSerialProxies = function (e) {
        e.send = function (t, r) {
            var i = n(t);
            e.send_raw(i, function (e) {
                r && r(e)
            })
        }, e.set_receive_handler = function (t) {
            e.set_receive_handler_raw(function (e) {
                t && (e = r(e), t(e))
            })
        }
    }
}, window.ScratchDeviceManager = new function () {
    function r(t) {
        return io(e.deviceManagerHost + t, {forceNew: !0})
    }

    function i() {
        for (var e = 0; e < t.length; e++)t[e].disconnect()
    }

    var e = this, t = [], n = !0;
    e.deviceManagerHost = "https://device-manager.scratch.mit.edu:3030", window.onbeforeunload = i, e.device_list = function (t, r, i, s) {
        var o = e.deviceManagerHost + "/" + t + "/list", u = {name: r, spec: i};
        $.ajax(o, {
            data: {data: JSON.stringify(u)}, dataType: "json", success: function (e, i, o) {
                n = !0, e.constructor == Array && s(e, t, r)
            }, error: function (e, t, r) {
                n = !1
            }
        })
    }, e.socket_open = function (e, n, i, s) {
        function o() {
            f() && s(l)
        }

        function u() {
            var e = t.indexOf(l);
            e >= 0 && t.splice(e, 1), f() && s(null)
        }

        function a() {
            l.disconnect()
        }

        function f() {
            return c !== null ? (clearTimeout(c), c = null, !0) : !1
        }

        var l = r("/" + n);
        t.push(l), l.on("deviceWasOpened", o), l.on("disconnect", u);
        var c = setTimeout(a, 1e4);
        l.emit("open", {deviceId: i, name: e})
    }, e.isConnected = function () {
        return n
    }
}, window.ScratchPlugin = new function () {
    function n(e) {
        var t = this;
        t.write_raw = function (t, n) {
            var r = e.write_raw(t);
            n && n(r)
        }, t.send_feature_report_raw = function (t, n) {
            var r = e.send_feature_report_raw(t);
            n && n(r)
        }, t.read_raw = function (t, n) {
            var r = e.read_raw(t);
            n && n(r)
        }, t.get_feature_report_raw = function (t, n) {
            var r = e.get_feature_report_raw(t);
            n && n(r)
        }, t.set_nonblocking = function (t, n) {
            var r = e.set_nonblocking(t);
            n && n(r)
        }, t.close = function () {
            e.close()
        }
    }

    function r(e) {
        var t = this;
        t.send_raw = function (t) {
            e.send_raw(t)
        }, t.close = function () {
            e.close()
        }, t.is_open = function (t) {
            var n = e.is_open();
            t && t(n)
        }, t.set_receive_handler_raw = function (t) {
            e.set_receive_handler_raw(t)
        }, t.set_error_handler = function (t) {
            e.set_error_handler(t)
        }
    }

    var e = this, t = "Scratch Device Plugin";
    e.useActiveX = window.hasOwnProperty("ActiveXObject"), e.axObjectName = "MITMediaLab.ScratchDevicePlugin", e.isAvailable = function () {
        return !!e.useActiveX || !!navigator.plugins[t]
    }, e.PluginWrapper = function (e) {
        var t = this;
        t.hid_list = function (t, n, r) {
            var i = e.hid_list(n, r);
            t && t(i)
        }, t.hid_open = function (t, r) {
            var i = e.hid_open_raw(t);
            i && (i = new n(i), ScratchProxies.AddHidProxies(i)), r && r(i)
        }, t.serial_list = function (t) {
            var n = e.serial_list();
            t && t(n)
        }, t.serial_open = function (t, n, i) {
            var s = e.serial_open_raw(t, n);
            s && (s = new r(s), ScratchProxies.AddSerialProxies(s)), i && i(s)
        }, t.reset = function () {
            e.reset()
        }, t.version = function (t) {
            var n = e.version();
            t && t(n)
        }
    }
}, window.ScratchDeviceHost = new function () {
    function u(e, t) {
        var n = (r++).toString();
        o[n] = t, i.postMessage([n, e])
    }

    function a(e) {
        var t = this;
        t.write_raw = function (t, n) {
            var r = ["write_raw", e, t];
            u(r, function (e) {
                n && n(e)
            })
        }, t.send_feature_report_raw = function (t, n) {
            var r = ["send_feature_report_raw", e, t];
            u(r, function (e) {
                n && n(e)
            })
        }, t.read_raw = function (t, n) {
            var r = ["read_raw", e, t];
            u(r, function (e) {
                n && n(e)
            })
        }, t.get_feature_report_raw = function (t, n) {
            var r = ["get_feature_report_raw", e, t];
            u(r, function (e) {
                n && n(e)
            })
        }, t.set_nonblocking = function (t, n) {
            var r = ["set_nonblocking", e, t];
            u(r, function (e) {
                n && n(e)
            })
        }, t.close = function () {
            u(["close", e])
        }
    }

    function l(e) {
        var t = this;
        t.receiveHandler = undefined, t.errorHandler = undefined, f[e] = t, t.send_raw = function (t) {
            var n = ["serial_send_raw", e, t];
            u(n)
        }, t.close = function () {
            var t = ["serial_close", e];
            u(t)
        }, t.is_open = function (t) {
            var n = ["serial_is_open", e];
            u(n, function (e) {
                t && t(e)
            })
        }, t.set_receive_handler_raw = function (n) {
            t.receiveHandler = n;
            var r = ["serial_recv_start", e];
            u(r)
        }, t.set_error_handler = function (e) {
            t.errorHandler = e
        }
    }

    var e = this, t = !1;
    e.isAvailable = function () {
        return t
    };
    if (!(window.chrome && window.chrome.runtime && window.chrome.runtime.connect))return;
    var n = "clmabinlolakdafkoajkfjjengcdmnpm", r = 0, i = chrome.runtime.connect(n);
    console.assert(i, "Failed to create port");
    var s = {};
    i.onMessage.addListener(function (e) {
        var t = e[0];
        if (t == "@") {
            var n = e[1], r = e[2], i = o[n];
            delete o[n], i && i(r)
        } else {
            var u = s[t];
            u ? u(e) : console.log("SDH-Page: Unrecognized message " + e)
        }
    }), s.serialRecv = function (e) {
        var t = e[1], n = e[2], r = f[t];
        r && r.receiveHandler && r.receiveHandler(n)
    }, s.serialError = function (e) {
        var t = e[1], n = e[2]
            , r = f[t];
        r && r.errorHandler && r.errorHandler(n)
    };
    var o = {};
    u(["version"], function (e) {
        t = !0
    }), e.hid_list = function (e, t, n) {
        var r = ["hid_list", t || 0, n || 0];
        u(r, function (t) {
            e && e(t)
        })
    }, e.hid_open = function (e, t) {
        var n = ["hid_open_raw", e];
        u(n, function (n) {
            var r;
            if (n) {
                r = new a(e), ScratchProxies.AddHidProxies(r);
                var i = ["claim", e];
                u(i)
            }
            t && t(r)
        })
    }, e.serial_list = function (e) {
        u(["serial_list"], function (t) {
            e && e(t)
        })
    }, e.serial_open = function (e, t, n) {
        var r = ["serial_open_raw", e];
        t && r.push(t), u(r, function (t) {
            var r;
            if (t) {
                r = new l(e), ScratchProxies.AddSerialProxies(r);
                var i = ["claim", e];
                u(i)
            }
            n && n(r)
        })
    }, e.reset = function () {
        u(["reset"])
    }, e.version = function (e) {
        u(["version"], function (t) {
            e && e(t)
        })
    };
    var f = {}
}, window.ScratchExtensions = new function () {
    function h() {
        for (var e in n)n[e]._shutdown();
        n = {}, b()
    }

    function d(e, t, n) {
        var r = setTimeout(function () {
            r = null, n()
        }, e);
        return function () {
            r !== null && (clearTimeout(r), t.apply(this, arguments))
        }
    }

    function v() {
        var r = {}, i;
        for (i in s)if (!o[i]) {
            var u = s[i];
            u.type == "hid" ? (r.hid || (r.hid = {}), r.hid[u.vendor + "_" + u.product] = i) : r[u.type] = i
        }
        for (var a in r) {
            if (!r.hasOwnProperty(a))continue;
            if (p.hasOwnProperty(a))continue;
            if (t && a == "hid") {
                var f = r.hid;
                p[a] = !0, t.hid_list(d(e, function (e) {
                    delete p.hid;
                    for (var t = 0; t < e.length; t++) {
                        var r = e[t].vendor_id + "_" + e[t].product_id, i = f[r];
                        i && n[i]._deviceConnected(new S(e[t], i))
                    }
                }, function () {
                    delete p.hid, console.log("Warning: HID list timed out")
                }))
            } else if (t && a == "serial") i = r.serial, p[a] = !0, t.serial_list(d(e, function (e) {
                delete p.serial;
                for (var t = 0; t < e.length; t++)n[i]._deviceConnected(new x(e[t], i))
            }, function () {
                delete p.serial, console.log("Warning: HID list timed out")
            })); else if (ScratchDeviceManager) {
                i = r[a], p[a] = !0;
                function l(e) {
                    return function () {
                        g(e)
                    }
                }

                ScratchDeviceManager.device_list(a, i, s[i], d(e, m, l(a)))
            }
        }
        w() || b()
    }

    function m(e, t, r) {
        delete p[t];
        for (var i = 0; i < e.length; ++i) {
            var s = Devices[t], o = e[i].id || e[i], u = new s(o, t, r);
            n[r]._deviceConnected(u)
        }
    }

    function g(e) {
        delete p[e], console.log("Warning: Device Manager list timed out for type " + e)
    }

    function y() {
        if (u || !w())return;
        u = setInterval(v, 500)
    }

    function b() {
        u && clearInterval(u), u = null
    }

    function w() {
        for (var e in s)if (!o[e])return !0;
        return !1
    }

    function E() {
        if (t)return;
        try {
            if (f) t = Scratch.FlashApp.ASobj.getPlugin(); else if (window.ScratchDeviceHost && window.ScratchDeviceHost.isAvailable()) t = window.ScratchDeviceHost; else {
                if (window.ScratchPlugin.useActiveX) t = new ActiveXObject(window.ScratchPlugin.axObjectName); else {
                    var e = document.createElement("div");
                    document.getElementById("scratch").parentNode.appendChild(e), e.innerHTML = '<object type="application/x-scratchdeviceplugin" width="1" height="1"> </object>', t = e.firstChild
                }
                t = new window.ScratchPlugin.PluginWrapper(t)
            }
        } catch (n) {
            console.error("Error creating plugin or wrapper:", n), t = null
        }
        setTimeout(y, 100)
    }

    function S(e, r) {
        function u() {
            setTimeout(function () {
                s.close(), n[r]._deviceRemoved(s)
            }, 0)
        }

        var i = null, s = this;
        this.id = e.path, this.info = e, this.open = function (e) {
            t.hid_open(s.id, function (t) {
                i = t, i && (o[r] = s, i.set_nonblocking(!0)), e && e(t ? s : null)
            })
        }, this.close = function () {
            if (!i)return;
            i.close(), delete o[r], i = null, y()
        }, this.write = function (e, t) {
            if (!i)return;
            i.write(e, function (e) {
                e < 0 && u(), t && t(e)
            })
        }, this.read = function (e, t) {
            if (!i)return null;
            t || (t = 65), i.read(t, function (t) {
                t.byteLength == 0 && u(), e(t)
            })
        }
    }

    function x(e, n) {
        var r = null, i = this;
        this.id = e, this.open = function (s, u) {
            t.serial_open(i.id, s, function (t) {
                r = t, r && (o[n] = i, r.set_error_handler(function (t) {
                    alert("Serial device error\n\nDevice: " + e + "\nError: " + t)
                })), u && u(t ? i : null)
            })
        }, this.close = function () {
            if (!r)return;
            r.close(), delete o[n], r = null, y()
        }, this.send = function (e) {
            if (!r)return;
            r.send(e)
        }, this.set_receive_handler = function (e) {
            if (!r)return;
            r.set_receive_handler(e)
        }
    }

    function T(e, t, r) {
        function u(e, t) {
            function i(e, n) {
                if (r.hasOwnProperty(e)) {
                    var i = r[e];
                    i && t.removeListener(e, i)
                }
                n && t.on(e, n), r[e] = n
            }

            var n = this, r = {};
            n.close = function () {
                t.close()
            }, n.setMotorOn = function (e, n) {
                t.emit("motorOn", {motorIndex: e, power: n})
            }, n.setMotorOff = function (e) {
                t.emit("motorOff", {motorIndex: e})
            }, n.setMotorBrake = function (e) {
                t.emit("motorBrake", {motorIndex: e})
            }, n.setLED = function (e) {
                t.emit("setLED", {rgb: e})
            }, n.playTone = function (e, n) {
                t.emit("playTone", {tone: e, ms: n})
            }, n.stopTone = function () {
                t.emit("stopTone")
            }, n.setSensorHandler = function (e) {
                i("sensorChanged", e)
            }, n.setDeviceWasClosedHandler = function (e) {
                i("disconnect", e), i("deviceWasClosed", e)
            }
        }

        function a() {
            setTimeout(function () {
                s.close(), n[r]._deviceRemoved(s)
            }, 0)
        }

        var i = null;
        this.ext_type = t, this.ext_name = r;
        var s = this;
        this.id = e, this.is_open = function () {
            return !!i
        }, this.open = function (e) {
            ScratchDeviceManager.socket_open(s.ext_name, s.ext_type, s.id, function (t) {
                t ? (i = new u(s.id, t), o[r] = s, i.setDeviceWasClosedHandler(a)) : (i = null, a()), e && e(i ? s : null)
            })
        }, this.close = function () {
            if (!i)return;
            i.close(), delete o[r], i = null, y()
        }, this.set_sensor_handler = function (e) {
            if (!i)return;
            i.setSensorHandler(e)
        }, this.set_motor_on = function (e, t) {
            i.setMotorOn(e, t)
        }, this.set_motor_brake = function (e) {
            i.setMotorBrake(e)
        }, this.set_motor_off = function (e) {
            i.setMotorOff(e)
        }, this.set_led = function (e) {
            i.setLED(e)
        }, this.play_tone = function (e, t) {
            i.playTone(e, t)
        }, this.stop_tone = function () {
            i.stopTone()
        }
    }

    function N(e, t, r) {
        function a() {
            setTimeout(function () {
                i.close(), n[i.ext_name]._deviceRemoved(i)
            }, 0)
        }

        var i = this;
        this.ext_name = r, this.ext_type = t, this.socket = null, this.id = e;
        var s = [], u = [];
        this.emit = function (e, t) {
            return i.socket && i.socket.emit(e, t), !!i.socket
        }, this.on = function (e, t) {
            i.is_open() ? i.socket.on(e, t) : s.push([e, t])
        }, this.once = function (e, t) {
            i.is_open() ? i.socket.once(e, t) : u.push([e, t])
        }, this.open = function (e) {
            ScratchDeviceManager.socket_open(i.ext_name, t, i.id, function (t) {
                i.socket = t, i.socket && (o[i.ext_name] = i, s.forEach(function (e) {
                    i.socket.on(e[0], e[1])
                }), u.forEach(function (e) {
                    i.socket.once(e[0], e[1])
                }), i.socket.on("disconnect", a), i.socket.on("deviceWasClosed", a)), e && e(i.socket ? i : null)
            })
        }, this.close = function () {
            if (!i.socket)return;
            i.socket.close(), delete o[i.ext_name], i.socket = null, y()
        }, this.is_open = function () {
            return !!i.socket
        }
    }

    var e = 5e3, t = null, n = {}, r = {}, i = {}, s = {}, o = {}, u = null, a = this,
        f = Scratch && Scratch.FlashApp && Scratch.FlashApp
                .ASobj && Scratch.FlashApp.ASobj.isOffline && Scratch.FlashApp.ASobj.isOffline(), l = function () {
            return !!window.ArrayBuffer && !!(f || window.ScratchPlugin && window.ScratchPlugin.isAvailable() || window.ScratchDeviceHost && window.ScratchDeviceHost.isAvailable())
        };
    a.register = function (e, o, u, a) {
        if (e in n)return console.log('Scratch extension "' + e + '" already exists!'), !1;
        n[e] = u, r[e] = o.blocks, o.menus && (i[e] = o.menus), a && (s[e] = a);
        var f = {extensionName: e, blockSpecs: o.blocks, url: o.url, menus: o.menus, javascriptURL: c};
        return Scratch.FlashApp.ASobj.ASloadExtension(f), a && (t ? y() : l() ? setTimeout(E, 10) : ScratchDeviceManager ? y() : window.ScratchPlugin.useActiveX && JSsetProjectBanner("Sorry, your version of Internet Explorer is not supported.  Please upgrade to version 10 or 11.")), !0
    };
    var c;
    a.loadExternalJS = function (e) {
        var t = document.createElement("script");
        t.src = e, c = e, document.getElementsByTagName("head")[0].appendChild(t)
    }, a.loadLocalJS = function (e) {
        try {
            (new Function(e))()
        } catch (t) {
            console.log(t.stack.toString())
        }
    }, a.unregister = function (e) {
        try {
            n[e]._shutdown()
        } catch (t) {
        }
        delete n[e], delete r[e], delete i[e], delete s[e]
    }, a.canAccessDevices = function () {
        return l()
    }, a.getReporter = function (e, t, r) {
        return n[e][t].apply(n[e], r)
    }, a.getReporterAsync = function (e, t, r, i) {
        var s = function (t) {
            Scratch.FlashApp.ASobj.ASextensionReporterDone(e, i, t)
        };
        n[e]._getStatus().status != 2 ? s(!1) : (r.push(s), n[e][t].apply(n[e], r))
    }, a.getReporterForceAsync = function (e, t, r, i) {
        var s = n[e][t].apply(n[e], r);
        Scratch.FlashApp.ASobj.ASextensionReporterDone(e, i, s)
    }, a.runCommand = function (e, t, r) {
        n[e][t].apply(n[e], r)
    }, a.runAsync = function (e, t, r, i) {
        var s = function () {
            Scratch.FlashApp.ASobj.ASextensionCallDone(e, i)
        };
        r.push(s), n[e][t].apply(n[e], r)
    }, a.getStatus = function (e) {
        if (e in n) {
            if (e in s)switch (s[e].type) {
                case"ble":
                case"wedo2":
                    if (!ScratchDeviceManager || !ScratchDeviceManager.isConnected())return {
                        status: 0,
                        msg: "Missing Scratch Device Manager"
                    };
                    break;
                default:
                    if (!l())return {status: 0, msg: "Missing browser plugin"}
            }
            return n[e]._getStatus()
        }
        return {status: 0, msg: "Not loaded"}
    }, a.stop = function (e) {
        var t = n[e];
        t._stop ? t._stop() : t.resetAll && t.resetAll()
    }, a.notify = function (e) {
        window.JSsetProjectBanner ? JSsetProjectBanner(e) : alert(e)
    }, a.resetPlugin = function () {
        t && t.reset && t.reset(), h()
    }, $(window).unload(function (e) {
        h()
    });
    var p = {};
    Devices = {ble: N, wedo2: T}
};
