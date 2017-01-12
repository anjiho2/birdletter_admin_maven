/*----------------------------------------------------------------------*/
/* jQuery Tipsy Plugin by Jason Frame https://twitter.com/jaz303 v 1.0.0a
/* http://onehackoranother.com/projects/jquery/tipsy/
/* (c) 2008-2010 jason frame [jason@onehackoranother.com]
/* releated under the MIT license
/*
/* !!! I did some modifications on that plugin! !!!
/*----------------------------------------------------------------------*/

(function ($) {
	function fixTitle($ele) {
		if ($ele.attr('title') || typeof ($ele.attr('original-title')) != 'string') {
			$ele.attr('original-title', $ele.attr('title') || '').removeAttr('title');
		}
	}
	function Tipsy(element, options) {
		this.$element = $(element);
		this.options = options;
		this.enabled = true;
		fixTitle(this.$element);
	}
	Tipsy.prototype = {
		show: function () {
			var title = this.getTitle();
			if (title && this.enabled) {
				var $tip = this.tip();
				$tip.find('.tipsy-inner')[this.options.html ? 'html' : 'text'](title);
				$tip[0].className = 'tipsy'; // reset classname in case of dynamic gravity
				$tip.remove().css({
					top: 0,
					left: 0,
					visibility: 'hidden',
					display: 'block'
				}).appendTo(this.options.appendTo);
				var pos = $.extend({}, this.$element.offset(), {
					width: this.$element[0].offsetWidth,
					height: this.$element[0].offsetHeight
				});
				if(this.options.appendTo != 'body')pos.top = pos.left = 0;
				var actualWidth = $tip[0].offsetWidth,
					actualHeight = $tip[0].offsetHeight;
				var gravity = (typeof this.options.gravity == 'function') ? this.options.gravity.call(this.$element[0]) : this.options.gravity;
				var tp;
				var mp;
				switch (gravity.charAt(0)) {
				case 'n':
					tp = {
						top: pos.top + pos.height + this.options.offset,
						left: pos.left + pos.width / 2 - actualWidth / 2
					};
					mp = {top:15,left:-actualWidth/2};
					break;
				case 's':
					tp = {
						top: pos.top - actualHeight - this.options.offset,
						left: pos.left + pos.width / 2 - actualWidth / 2
					};
					mp = {top:-actualHeight-15,left:-actualWidth/2};
					break;
				case 'e':
					tp = {
						top: pos.top + pos.height / 2 - actualHeight / 2,
						left: pos.left - actualWidth - this.options.offset
					};
					mp = {top:-actualHeight/2,left:-12-actualWidth};
					break;
				case 'w':
					tp = {
						top: pos.top + pos.height / 2 - actualHeight / 2,
						left: pos.left + pos.width + this.options.offset
					};
					mp = {top:-actualHeight/2,left:12};
					break;
				}
				if (gravity.length == 2) {
					if (gravity.charAt(1) == 'w') {
						//tp.left = pos.left + pos.width / 2 - 15;
						tp.left = pos.left - 5;
						mp.left += actualWidth/2-15;
					} else {
						//tp.left = pos.left + pos.width / 2 - actualWidth + 15;
						mp.left -= actualWidth/2-15;
						tp.left = pos.left + pos.width - actualWidth + 5;
					}
				}
				
				if(this.options.followMouse){
					$(document).bind('mousemove.tipsy',function(e){
						var x = e.pageX+mp.left, y = e.pageY+mp.top;
						$tip.css({
							left: x,
							top: y
						});
					});
				}
				
				$tip.css(tp).addClass('tipsy-' + gravity);
				if (this.options.fade) {
					$tip.stop().css({
						opacity: 0,
						display: 'block',
						visibility: 'visible'
					}).animate({
						opacity: this.options.opacity
					});
				} else {
					$tip.css({
						visibility: 'visible',
						opacity: this.options.opacity
					});
				}
			}
		},

		hide: function () {
			if(this.options.followMouse){
				$(document).unbind('mousemove.tipsy');
			}
			if (this.options.fade) {
				this.tip().stop().fadeOut(function () {
					$(this).remove();
				});
			} else {
				this.tip().remove();
			}
		},

		getTitle: function () {
			var title, $e = this.$element,
				o = this.options;
			fixTitle($e);
			if (typeof o.title == 'string') {
				title = $e.attr(o.title == 'title' ? 'original-title' : o.title);
			} else if (typeof o.title == 'function') {
				title = o.title.call($e[0]);
			}
			if(!title) title = o.fallback;
			title = ('' + title).replace(/(^\s*|\s*$)/, "");
			return title || o.fallback;
		},
		
		setTitel: function(title) {
			this.options.fallback = title;
		},

		tip: function () {
			if (!this.$tip) {
				this.$tip = $('<div class="tipsy"></div>').html('<div class="tipsy-arrow"></div><div class="tipsy-outer"><div class="tipsy-inner"/></div></div>');
			}
			return this.$tip;
		},

		validate: function () {
			if (!this.$element[0].parentNode) {
				this.hide();
				this.$element = null;
				this.options = null;
			}
		},

		enable: function () {
			this.enabled = true;
		},
		disable: function () {
			this.enabled = false;
		},
		update: function () {
			var $tip = this.tip();
			$tip.find('.tipsy-inner')[this.options.html ? 'html' : 'text'](this.options.fallback);
		},
		toggleEnabled: function () {
			this.enabled = !this.enabled;
		}
	};

	$.fn.tipsy = function (options) {

		if (options === true) {
			return this.data('tipsy');
		} else if (typeof options == 'string') {
			return this.data('tipsy')[options](arguments[1], arguments[2]);
		}

		options = $.extend({}, $.fn.tipsy.defaults, options);

		function get(ele) {
			var tipsy = $.data(ele, 'tipsy');
			if (!tipsy) {
				tipsy = new Tipsy(ele, $.fn.tipsy.elementOptions(ele, options));
				$.data(ele, 'tipsy', tipsy);
			}
			return tipsy;
		}

		function enter() {
			var tipsy = get(this);
			tipsy.hoverState = 'in';
			if (options.delayIn == 0) {
				tipsy.show();
			} else {
				setTimeout(function () {
					if (tipsy.hoverState == 'in') tipsy.show();
				}, options.delayIn);
			}
		};

		function leave() {
			var tipsy = get(this);
			tipsy.hoverState = 'out';
			if (options.delayOut == 0) {
				tipsy.hide();
			} else {
				setTimeout(function () {
					if (tipsy.hoverState == 'out') tipsy.hide();
				}, options.delayOut);
			}
		};

		if (!options.live) this.each(function () {
			get(this);
		});
		if (options.trigger != 'manual') {
			var binder = options.live ? 'live' : 'bind',
				eventIn = options.trigger == 'hover' ? 'mouseenter' : 'focus',
				eventOut = options.trigger == 'hover' ? 'mouseleave' : 'blur';
			this[binder](eventIn+'.tipsy', enter)[binder](eventOut+'.tipsy', leave);
		}

		return this;

	};

	$.fn.tipsy.defaults = {
		delayIn: 0,
		delayOut: 0,
		fade: false,
		fallback: '',
		gravity: 'n',
		html: false,
		live: false,
		offset: 0,
		opacity: 0.8,
		//CUSTOM followMouse
		followMouse: false,
		appendTo: 'body',
		title: 'title',
		trigger: 'hover'
	};

	// Overwrite this method to provide options on a per-element basis.
	// For example, you could store the gravity in a 'tipsy-gravity' attribute:
	// return $.extend({}, options, {gravity: $(ele).attr('tipsy-gravity') || 'n' });
	// (remember - do not modify 'options' in place!)
	$.fn.tipsy.elementOptions = function (ele, options) {
		return $.metadata ? $.extend({}, options, $(ele).metadata()) : options;
	};

	$.fn.tipsy.autoNS = function () {
		return $(this).offset().top > ($(document).scrollTop() + $(window).height() / 2) ? 's' : 'n';
	};

	$.fn.tipsy.autoWE = function () {
		return $(this).offset().left > ($(document).scrollLeft() + $(window).width() / 2) ? 'e' : 'w';
	};

})(jQuery);

/* --->> 2 - UItoTop jQuery Plugin 1.2 by Matt Varone - http://www.mattvarone.com/web-design/uitotop-jquery-plugin/
-------------------------------------------------------------------------------------------------------------------*/
(function($){
	$.fn.UItoTop = function(options) {

 		var defaults = {
    			text: 'To Top',
    			min: 200,
    			inDelay:0,
    			outDelay:0,
      			containerID: 'toTop',
    			containerHoverID: 'toTopHover',
    			scrollSpeed: 600,
    			easingType: 'linear'
 		    },
            settings = $.extend(defaults, options),
            containerIDhash = '#' + settings.containerID,
            containerHoverIDHash = '#'+settings.containerHoverID;
		
		$('body').append('<a href="#" id="'+settings.containerID+'"></a>');
		$(containerIDhash).hide().on('click.UItoTop',function(){
			$('html, body').animate({scrollTop:0}, settings.scrollSpeed, settings.easingType);
			$('#'+settings.containerHoverID, this).stop().animate({'opacity': 0 }, settings.inDelay, settings.easingType);
			return false;
		})
		.prepend('<span id="'+settings.containerHoverID+'"></span>')
		.hover(function() {
				$(containerHoverIDHash, this).show();
			}, function() { 
				$(containerHoverIDHash, this).hide();
			});

		$(window).scroll(function() {
			var sd = $(window).scrollTop();
			if(typeof document.body.style.maxHeight === "undefined") {
				$(containerIDhash).css({
					'position': 'absolute',
					'top': sd + $(window).height() - 50
				});
			}
			if ( sd > settings.min ) 
				$(containerIDhash).fadeIn();
			else 
				$(containerIDhash).fadeOut();
		});
	};
	var checkGap = 42;
	var gapsize = 10;
	
	function topBtnPos(){
		if($(window).scrollTop()+window.innerHeight  > $(document).height()-checkGap){
			gapsize = 10 + ($(window).scrollTop()+window.innerHeight +checkGap)-$(document).height()
		}else{
			gapsize = 10;
		}
		$("#toTop").css("bottom",gapsize+"px");
	}
	
	$(window).scroll(function() {
		topBtnPos();
	});
	
	$( window ).resize(function() {
		topBtnPos();
	});
})(jQuery);


/* ============================================================
 * bootstrap-dropdown.js v2.2.1
 * http://twitter.github.com/bootstrap/javascript.html#dropdowns
 * ============================================================
 * Copyright 2012 Twitter, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============================================================ */


!function ($) {
  "use strict"; // jshint ;_;
 /* DROPDOWN CLASS DEFINITION
  * ========================= */
  var toggle = '[data-toggle=dropdown]'
    , Dropdown = function (element) {
        var $el = $(element).on('click.dropdown.data-api', this.toggle)
        $('html').on('click.dropdown.data-api', function () {
          $el.parent().removeClass('open')
        })
      }
  Dropdown.prototype = {
    constructor: Dropdown
  , toggle: function (e) {
      var $this = $(this)
        , $parent
        , isActive
      if ($this.is('.disabled, :disabled')) return
      $parent = getParent($this)
      isActive = $parent.hasClass('open')
      clearMenus()
      if (!isActive) {
        $parent.toggleClass('open')
        $this.focus()
      }
      return false
    }

  , keydown: function (e) {
      var $this
        , $items
        , $active
        , $parent
        , isActive
        , index

      if (!/(38|40|27)/.test(e.keyCode)) return
      $this = $(this)
      e.preventDefault()
      e.stopPropagation()
      if ($this.is('.disabled, :disabled')) return
      $parent = getParent($this)
      isActive = $parent.hasClass('open')
      if (!isActive || (isActive && e.keyCode == 27)) return $this.click()
      $items = $('[role=menu] li:not(.divider) a', $parent)
      if (!$items.length) return
      index = $items.index($items.filter(':focus'))
      if (e.keyCode == 38 && index > 0) index--                                        // up
      if (e.keyCode == 40 && index < $items.length - 1) index++                        // down
      if (!~index) index = 0
      $items
        .eq(index)
        .focus()
    }
  }

  function clearMenus() {
    $(toggle).each(function () {
      getParent($(this)).removeClass('open')
    })
  }

  function getParent($this) {
    var selector = $this.attr('data-target')
      , $parent
    if (!selector) {
      selector = $this.attr('href')
      selector = selector && /#/.test(selector) && selector.replace(/.*(?=#[^\s]*$)/, '') //strip for ie7
    }
    $parent = $(selector)
    $parent.length || ($parent = $this.parent())
    return $parent
  }

  /* DROPDOWN PLUGIN DEFINITION
   * ========================== */
  $.fn.dropdown = function (option) {
    return this.each(function () {
      var $this = $(this)
        , data = $this.data('dropdown')
      if (!data) $this.data('dropdown', (data = new Dropdown(this)))
      if (typeof option == 'string') data[option].call($this)
    })
  }
  $.fn.dropdown.Constructor = Dropdown

  /* APPLY TO STANDARD DROPDOWN ELEMENTS
   * =================================== */

  $(document)
    .on('click.dropdown.data-api touchstart.dropdown.data-api', clearMenus)
    .on('click.dropdown touchstart.dropdown.data-api', '.dropdown form', function (e) { e.stopPropagation() })
    .on('click.dropdown.data-api touchstart.dropdown.data-api'  , toggle, Dropdown.prototype.toggle)
    .on('keydown.dropdown.data-api touchstart.dropdown.data-api', toggle + ', [role=menu]' , Dropdown.prototype.keydown)

}(window.jQuery);

/* ===========================================================
 * bootstrap-tooltip.js v2.2.1
 * http://twitter.github.com/bootstrap/javascript.html#tooltips
 * Inspired by the original jQuery.tipsy by Jason Frame
 * ===========================================================
 * Copyright 2012 Twitter, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ========================================================== */


!function ($) {

  "use strict"; // jshint ;_;
 /* TOOLTIP PUBLIC CLASS DEFINITION
  * =============================== */
  var Tooltip = function (element, options) {
    this.init('tooltip', element, options)
  }
  Tooltip.prototype = {
    constructor: Tooltip
  , init: function (type, element, options) {
      var eventIn
        , eventOut
      this.type = type
      this.$element = $(element)
      this.options = this.getOptions(options)
      this.enabled = true
      if (this.options.trigger == 'click') {
        this.$element.on('click.' + this.type, this.options.selector, $.proxy(this.toggle, this))
      } else if (this.options.trigger != 'manual') {
        eventIn = this.options.trigger == 'hover' ? 'mouseenter' : 'focus'
        eventOut = this.options.trigger == 'hover' ? 'mouseleave' : 'blur'
        this.$element.on(eventIn + '.' + this.type, this.options.selector, $.proxy(this.enter, this))
        this.$element.on(eventOut + '.' + this.type, this.options.selector, $.proxy(this.leave, this))
      }
      this.options.selector ?
        (this._options = $.extend({}, this.options, { trigger: 'manual', selector: '' })) :
        this.fixTitle()
    }

  , getOptions: function (options) {
      options = $.extend({}, $.fn[this.type].defaults, options, this.$element.data())
      if (options.delay && typeof options.delay == 'number') {
        options.delay = {
          show: options.delay
        , hide: options.delay
        }
      }
      return options
    }

  , enter: function (e) {
      var self = $(e.currentTarget)[this.type](this._options).data(this.type)
      if (!self.options.delay || !self.options.delay.show) return self.show()
      clearTimeout(this.timeout)
      self.hoverState = 'in'
      this.timeout = setTimeout(function() {
        if (self.hoverState == 'in') self.show()
      }, self.options.delay.show)
    }

  , leave: function (e) {
      var self = $(e.currentTarget)[this.type](this._options).data(this.type)
      if (this.timeout) clearTimeout(this.timeout)
      if (!self.options.delay || !self.options.delay.hide) return self.hide()
      self.hoverState = 'out'
      this.timeout = setTimeout(function() {
        if (self.hoverState == 'out') self.hide()
      }, self.options.delay.hide)
    }

  , show: function () {
      var $tip
        , inside
        , pos
        , actualWidth
        , actualHeight
        , placement
        , tp
      if (this.hasContent() && this.enabled) {
        $tip = this.tip()
        this.setContent()

        if (this.options.animation) {
          $tip.addClass('fade')
        }
        placement = typeof this.options.placement == 'function' ?
          this.options.placement.call(this, $tip[0], this.$element[0]) :
          this.options.placement

        inside = /in/.test(placement)
        $tip
          .detach()
          .css({ top: 0, left: 0, display: 'block' })
          .insertAfter(this.$element)

        pos = this.getPosition(inside)
        actualWidth = $tip[0].offsetWidth
        actualHeight = $tip[0].offsetHeight

        switch (inside ? placement.split(' ')[1] : placement) {
          case 'bottom':
            tp = {top: pos.top + pos.height, left: pos.left + pos.width / 2 - actualWidth / 2}
            break
          case 'top':
            tp = {top: pos.top - actualHeight, left: pos.left + pos.width / 2 - actualWidth / 2}
            break
          case 'left':
            tp = {top: pos.top + pos.height / 2 - actualHeight / 2, left: pos.left - actualWidth}
            break
          case 'right':
            tp = {top: pos.top + pos.height / 2 - actualHeight / 2, left: pos.left + pos.width}
            break
        }
        $tip
          .offset(tp)
          .addClass(placement)
          .addClass('in')
      }
    }

  , setContent: function () {
      var $tip = this.tip()
        , title = this.getTitle()

      $tip.find('.tooltip-inner')[this.options.html ? 'html' : 'text'](title)
      $tip.removeClass('fade in top bottom left right')
    }
  , hide: function () {
      var that = this
        , $tip = this.tip()
      $tip.removeClass('in')
      function removeWithAnimation() {
        var timeout = setTimeout(function () {
          $tip.off($.support.transition.end).detach()
        }, 500)
        $tip.one($.support.transition.end, function () {
          clearTimeout(timeout)
          $tip.detach()
        })
      }
      $.support.transition && this.$tip.hasClass('fade') ?
        removeWithAnimation() :
        $tip.detach()

      return this
    }

  , fixTitle: function () {
      var $e = this.$element
      if ($e.attr('title') || typeof($e.attr('data-original-title')) != 'string') {
        $e.attr('data-original-title', $e.attr('title') || '').removeAttr('title')
      }
    }
  , hasContent: function () {
      return this.getTitle()
    }

  , getPosition: function (inside) {
      return $.extend({}, (inside ? {top: 0, left: 0} : this.$element.offset()), {
        width: this.$element[0].offsetWidth
      , height: this.$element[0].offsetHeight
      })
    }
  , getTitle: function () {
      var title
        , $e = this.$element
        , o = this.options

      title = $e.attr('data-original-title')
        || (typeof o.title == 'function' ? o.title.call($e[0]) :  o.title)

      return title
    }
  , tip: function () {
      return this.$tip = this.$tip || $(this.options.template)
    }

  , validate: function () {
      if (!this.$element[0].parentNode) {
        this.hide()
        this.$element = null
        this.options = null
      }
    }
  , enable: function () {
      this.enabled = true
    }
  , disable: function () {
      this.enabled = false
    }
  , toggleEnabled: function () {
      this.enabled = !this.enabled
    }
  , toggle: function (e) {
      var self = $(e.currentTarget)[this.type](this._options).data(this.type)
      self[self.tip().hasClass('in') ? 'hide' : 'show']()
    }
  , destroy: function () {
      this.hide().$element.off('.' + this.type).removeData(this.type)
    }
  }


 /* TOOLTIP PLUGIN DEFINITION
  * ========================= */

  $.fn.tooltip = function ( option ) {
    return this.each(function () {
      var $this = $(this)
        , data = $this.data('tooltip')
        , options = typeof option == 'object' && option
      if (!data) $this.data('tooltip', (data = new Tooltip(this, options)))
      if (typeof option == 'string') data[option]()
    })
  }

  $.fn.tooltip.Constructor = Tooltip

  $.fn.tooltip.defaults = {
    animation: true
  , placement: 'top'
  , selector: false
  , template: '<div class="tooltip"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>'
  , trigger: 'hover'
  , title: ''
  , delay: 0
  , html: false
  }

}(window.jQuery);

/* ===========================================================
 * bootstrap-popover.js v2.2.1
 * http://twitter.github.com/bootstrap/javascript.html#popovers
 * ===========================================================
 * Copyright 2012 Twitter, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =========================================================== */


!function ($) {

  "use strict"; // jshint ;_;
 /* POPOVER PUBLIC CLASS DEFINITION
  * =============================== */
  var Popover = function (element, options) {
    this.init('popover', element, options)
  }
  /* NOTE: POPOVER EXTENDS BOOTSTRAP-TOOLTIP.js
     ========================================== */
  Popover.prototype = $.extend({}, $.fn.tooltip.Constructor.prototype, {
    constructor: Popover
  , setContent: function () {
      var $tip = this.tip()
        , title = this.getTitle()
        , content = this.getContent()
      $tip.find('.popover-title')[this.options.html ? 'html' : 'text'](title)
      $tip.find('.popover-content > *')[this.options.html ? 'html' : 'text'](content)

      $tip.removeClass('fade top bottom left right in')
    }

  , hasContent: function () {
      return this.getTitle() || this.getContent()
    }
  , getContent: function () {
      var content
        , $e = this.$element
        , o = this.options
      content = $e.attr('data-content')
        || (typeof o.content == 'function' ? o.content.call($e[0]) :  o.content)
      return content
    }

  , tip: function () {
      if (!this.$tip) {
        this.$tip = $(this.options.template)
      }
      return this.$tip
    }
  , destroy: function () {
      this.hide().$element.off('.' + this.type).removeData(this.type)
    }
  })


 /* POPOVER PLUGIN DEFINITION
  * ======================= */

  $.fn.popover = function (option) {
    return this.each(function () {
      var $this = $(this)
        , data = $this.data('popover')
        , options = typeof option == 'object' && option
      if (!data) $this.data('popover', (data = new Popover(this, options)))
      if (typeof option == 'string') data[option]()
    })
  }

  $.fn.popover.Constructor = Popover

  $.fn.popover.defaults = $.extend({} , $.fn.tooltip.defaults, {
    placement: 'right'
  , trigger: 'click'
  , content: ''
  , template: '<div class="popover"><div class="arrow"></div><div class="popover-inner"><h3 class="popover-title"></h3><div class="popover-content"><p></p></div></div></div>'
  })

}(window.jQuery);


/*----------------------------------------------------------------------*/
/* jQuery miniColors: A small color selector
/* Copyright 2011 Cory LaViska for A Beautiful Site, LLC.
/* http://abeautifulsite.net/blog/2011/02/jquery-minicolors-a-color-selector-for-input-controls/
/* Dual licensed under the MIT or GPL Version 2 licenses
/*----------------------------------------------------------------------*/

(function ($) {
	$.fn.miniColors = function (o, data) {
		var create = function (input, o, data) {
				//
				// Creates a new instance of the miniColors selector
				//
				// Determine initial color (defaults to white)
				var color = cleanHex(input.val());
				if (!color) color = 'FFFFFF';
				var hsb = hex2hsb(color);

				// Create trigger
				var trigger = $('<a class="miniColors-trigger" style="background-color: #' + color + '" href="#"></a>');
				trigger.insertAfter(input);

				// Add necessary attributes
				input.addClass('miniColors').attr('maxlength', 7).attr('autocomplete', 'off');

				// Set input data
				input.data('trigger', trigger);
				input.data('hsb', hsb);
				if (o.change) input.data('change', o.change);

				// Handle options
				if (o.readonly) input.attr('readonly', true);
				if (o.disabled) disable(input);

				// Show selector when trigger is clicked
				trigger.bind('click.miniColors', function (event) {
					event.preventDefault();
					input.trigger('focus');
				});

				// Show selector when input receives focus
				input.bind('focus.miniColors', function (event) {
					show(input);
				});

				// Hide on blur
				input.bind('blur.miniColors', function (event) {
					var hex = cleanHex(input.val());
					input.val(hex ? '#' + hex : '');
				});

				// Hide when tabbing out of the input
				input.bind('keydown.miniColors', function (event) {
					if (event.keyCode === 9) hide(input);
				});

				// Update when color is typed in
				input.bind('keyup.miniColors', function (event) {
					// Remove non-hex characters
					var filteredHex = input.val().replace(/[^A-F0-9#]/ig, '');
					input.val(filteredHex);
					if (!setColorFromInput(input)) {
						// Reset trigger color when color is invalid
						input.data('trigger').css('backgroundColor', '#FFF');
					}
				});

				// Handle pasting
				input.bind('paste.miniColors', function (event) {
					// Short pause to wait for paste to complete
					setTimeout(function () {
						input.trigger('keyup');
					}, 5);
				});

			};

		var destroy = function (input) {
				//
				// Destroys an active instance of the miniColors selector
				//
				hide();

				input = $(input);
				input.data('trigger').remove();
				input.removeAttr('autocomplete');
				input.removeData('trigger');
				input.removeData('selector');
				input.removeData('hsb');
				input.removeData('huePicker');
				input.removeData('colorPicker');
				input.removeData('mousebutton');
				input.removeData('moving');
				input.unbind('click.miniColors');
				input.unbind('focus.miniColors');
				input.unbind('blur.miniColors');
				input.unbind('keyup.miniColors');
				input.unbind('keydown.miniColors');
				input.unbind('paste.miniColors');
				$(document).unbind('mousedown.miniColors');
				$(document).unbind('mousemove.miniColors');
			};

		var enable = function (input) {
				//
				// Disables the input control and the selector
				//
				input.attr('disabled', false);
				input.data('trigger').css('opacity', 1);
			};
		var disable = function (input) {
				//
				// Disables the input control and the selector
				//
				hide(input);
				input.attr('disabled', true);
				input.data('trigger').css('opacity', .5);
			};


		var show = function (input) {
				//
				// Shows the miniColors selector
				//
				if (input.attr('disabled')) return false;
				// Hide all other instances 
				hide();
				// Generate the selector
				var selector = $('<div class="miniColors-selector"></div>');
				selector.append('<div class="miniColors-colors" style="background-color: #FFF;"><div class="miniColors-colorPicker"></div></div>');
				selector.append('<div class="miniColors-hues"><div class="miniColors-huePicker"></div></div>');
				selector.css({
					top: input.is(':visible') ? input.offset().top + input.outerHeight() : input.data('trigger').offset().top + input.data('trigger').outerHeight(),
					left: input.is(':visible') ? input.offset().left : input.data('trigger').offset().left,
					display: 'none'
				}).addClass(input.attr('class'));

				// Set background for colors
				var hsb = input.data('hsb');
				selector.find('.miniColors-colors').css('backgroundColor', '#' + hsb2hex({
					h: hsb.h,
					s: 100,
					b: 100
				}));

				// Set colorPicker position
				var colorPosition = input.data('colorPosition');
				if (!colorPosition) colorPosition = getColorPositionFromHSB(hsb);
				selector.find('.miniColors-colorPicker').css('top', colorPosition.y + 'px').css('left', colorPosition.x + 'px');

				// Set huePicker position
				var huePosition = input.data('huePosition');
				if (!huePosition) huePosition = getHuePositionFromHSB(hsb);
				selector.find('.miniColors-huePicker').css('top', huePosition.y + 'px');


				// Set input data
				input.data('selector', selector);
				input.data('huePicker', selector.find('.miniColors-huePicker'));
				input.data('colorPicker', selector.find('.miniColors-colorPicker'));
				input.data('mousebutton', 0);

				$('BODY').append(selector);
				selector.fadeIn(100);

				// Prevent text selection in IE
				selector.bind('selectstart', function () {
					return false;
				});

				$(document).bind('mousedown.miniColors', function (event) {
					input.data('mousebutton', 1);

					if ($(event.target).parents().andSelf().hasClass('miniColors-colors')) {
						event.preventDefault();
						input.data('moving', 'colors');
						moveColor(input, event);
					}
					if ($(event.target).parents().andSelf().hasClass('miniColors-hues')) {
						event.preventDefault();
						input.data('moving', 'hues');
						moveHue(input, event);
					}
					if ($(event.target).parents().andSelf().hasClass('miniColors-selector')) {
						event.preventDefault();
						return;
					}
					if ($(event.target).parents().andSelf().hasClass('miniColors')) return;

					hide(input);
				});
				$(document).bind('mouseup.miniColors', function (event) {
					input.data('mousebutton', 0);
					input.removeData('moving');
				});
				$(document).bind('mousemove.miniColors', function (event) {
					if (input.data('mousebutton') === 1) {
						if (input.data('moving') === 'colors') moveColor(input, event);
						if (input.data('moving') === 'hues') moveHue(input, event);
					}
				});

			};


		var hide = function (input) {
				//
				// Hides one or more miniColors selectors
				//
				// Hide all other instances if input isn't specified
				if (!input) input = '.miniColors';

				$(input).each(function () {
					var selector = $(this).data('selector');
					$(this).removeData('selector');
					$(selector).fadeOut(100, function () {
						$(this).remove();
					});
				});
				$(document).unbind('mousedown.miniColors');
				$(document).unbind('mousemove.miniColors');
			};

		var moveColor = function (input, event) {
				var colorPicker = input.data('colorPicker');
				colorPicker.hide();
				var position = {
					x: event.clientX - input.data('selector').find('.miniColors-colors').offset().left + $(document).scrollLeft() - 5,
					y: event.clientY - input.data('selector').find('.miniColors-colors').offset().top + $(document).scrollTop() - 5
				};
				if (position.x <= -5) position.x = -5;
				if (position.x >= 144) position.x = 144;
				if (position.y <= -5) position.y = -5;
				if (position.y >= 144) position.y = 144;
				input.data('colorPosition', position);
				colorPicker.css('left', position.x).css('top', position.y).show();
				// Calculate saturation
				var s = Math.round((position.x + 5) * .67);
				if (s < 0) s = 0;
				if (s > 100) s = 100;
				// Calculate brightness
				var b = 100 - Math.round((position.y + 5) * .67);
				if (b < 0) b = 0;
				if (b > 100) b = 100;
				// Update HSB values
				var hsb = input.data('hsb');
				hsb.s = s;
				hsb.b = b;
				// Set color
				setColor(input, hsb, true);
			};


		var moveHue = function (input, event) {
				var huePicker = input.data('huePicker');
				huePicker.hide();
				var position = {
					y: event.clientY - input.data('selector').find('.miniColors-colors').offset().top + $(document).scrollTop() - 1
				};

				if (position.y <= -1) position.y = -1;
				if (position.y >= 149) position.y = 149;
				input.data('huePosition', position);
				huePicker.css('top', position.y).show();

				// Calculate hue
				var h = Math.round((150 - position.y - 1) * 2.4);
				if (h < 0) h = 0;
				if (h > 360) h = 360;

				// Update HSB values
				var hsb = input.data('hsb');
				hsb.h = h;

				// Set color
				setColor(input, hsb, true);

			};


		var setColor = function (input, hsb, updateInputValue) {
				input.data('hsb', hsb);
				var hex = hsb2hex(hsb);
				if (updateInputValue) input.val('#' + hex);
				input.data('trigger').css('backgroundColor', '#' + hex);
				if (input.data('selector')) input.data('selector').find('.miniColors-colors').css('backgroundColor', '#' + hsb2hex({
					h: hsb.h,
					s: 100,
					b: 100
				}));

				if (input.data('change')) {
					input.data('change').call(input, '#' + hex, hsb2rgb(hsb));
				}
			};


		var setColorFromInput = function (input) {
				// Don't update if the hex color is invalid
				var hex = cleanHex(input.val());
				if (!hex) return false;

				// Get HSB equivalent
				var hsb = hex2hsb(hex);

				// If color is the same, no change required
				var currentHSB = input.data('hsb');
				if (hsb.h === currentHSB.h && hsb.s === currentHSB.s && hsb.b === currentHSB.b) return true;

				// Set colorPicker position
				var colorPosition = getColorPositionFromHSB(hsb);
				var colorPicker = $(input.data('colorPicker'));
				colorPicker.css('top', colorPosition.y + 'px').css('left', colorPosition.x + 'px');

				// Set huePosition position
				var huePosition = getHuePositionFromHSB(hsb);
				var huePicker = $(input.data('huePicker'));
				huePicker.css('top', huePosition.y + 'px');

				setColor(input, hsb, false);

				return true;
			};


		var getColorPositionFromHSB = function (hsb) {
				var x = Math.ceil(hsb.s / .67);
				if (x < 0) x = 0;
				if (x > 150) x = 150;

				var y = 150 - Math.ceil(hsb.b / .67);
				if (y < 0) y = 0;
				if (y > 150) y = 150;

				return {
					x: x - 5,
					y: y - 5
				};
			};


		var getHuePositionFromHSB = function (hsb) {
				var y = 150 - (hsb.h / 2.4);
				if (y < 0) h = 0;
				if (y > 150) h = 150;

				return {
					y: y - 1
				};
			};


		var cleanHex = function (hex) {
				//
				// Turns a dirty hex string into clean, 6-character hex color
				//
				hex = hex.replace(/[^A-Fa-f0-9]/, '');
				if (hex.length == 3) {
					hex = hex[0] + hex[0] + hex[1] + hex[1] + hex[2] + hex[2];
				}
				return hex.length === 6 ? hex : null;
			};


		var hsb2rgb = function (hsb) {
				var rgb = {};
				var h = Math.round(hsb.h);
				var s = Math.round(hsb.s * 255 / 100);
				var v = Math.round(hsb.b * 255 / 100);
				if (s == 0) {
					rgb.r = rgb.g = rgb.b = v;
				} else {
					var t1 = v;
					var t2 = (255 - s) * v / 255;
					var t3 = (t1 - t2) * (h % 60) / 60;
					if (h == 360) h = 0;
					if (h < 60) {
						rgb.r = t1;
						rgb.b = t2;
						rgb.g = t2 + t3;
					} else if (h < 120) {
						rgb.g = t1;
						rgb.b = t2;
						rgb.r = t1 - t3;
					} else if (h < 180) {
						rgb.g = t1;
						rgb.r = t2;
						rgb.b = t2 + t3;
					} else if (h < 240) {
						rgb.b = t1;
						rgb.r = t2;
						rgb.g = t1 - t3;
					} else if (h < 300) {
						rgb.b = t1;
						rgb.g = t2;
						rgb.r = t2 + t3;
					} else if (h < 360) {
						rgb.r = t1;
						rgb.g = t2;
						rgb.b = t1 - t3;
					} else {
						rgb.r = 0;
						rgb.g = 0;
						rgb.b = 0;
					}
				}
				return {
					r: Math.round(rgb.r),
					g: Math.round(rgb.g),
					b: Math.round(rgb.b)
				};
			};


		var rgb2hex = function (rgb) {
				var hex = [
				rgb.r.toString(16), rgb.g.toString(16), rgb.b.toString(16)];
				$.each(hex, function (nr, val) {
					if (val.length == 1) hex[nr] = '0' + val;
				});
				return hex.join('');
			};

		var hex2rgb = function (hex) {
				var hex = parseInt(((hex.indexOf('#') > -1) ? hex.substring(1) : hex), 16);
				return {
					r: hex >> 16,
					g: (hex & 0x00FF00) >> 8,
					b: (hex & 0x0000FF)
				};
			};


		var rgb2hsb = function (rgb) {
				var hsb = {
					h: 0,
					s: 0,
					b: 0
				};
				var min = Math.min(rgb.r, rgb.g, rgb.b);
				var max = Math.max(rgb.r, rgb.g, rgb.b);
				var delta = max - min;
				hsb.b = max;
				hsb.s = max != 0 ? 255 * delta / max : 0;
				if (hsb.s != 0) {
					if (rgb.r == max) {
						hsb.h = (rgb.g - rgb.b) / delta;
					} else if (rgb.g == max) {
						hsb.h = 2 + (rgb.b - rgb.r) / delta;
					} else {
						hsb.h = 4 + (rgb.r - rgb.g) / delta;
					}
				} else {
					hsb.h = -1;
				}
				hsb.h *= 60;
				if (hsb.h < 0) {
					hsb.h += 360;
				}
				hsb.s *= 100 / 255;
				hsb.b *= 100 / 255;
				return hsb;
			};

		var hex2hsb = function (hex) {
				var hsb = rgb2hsb(hex2rgb(hex));
				// Zero out hue marker for black, white, and grays (saturation === 0)
				if (hsb.s === 0) hsb.h = 360;
				return hsb;
			};

		var hsb2hex = function (hsb) {
				return rgb2hex(hsb2rgb(hsb));
			};
		//
		// Handle calls to $([selector]).miniColors()
		//
		switch (o) {

		case 'readonly':
			$(this).each(function () {
				$(this).attr('readonly', data);
			});
			return $(this);
			break;
		case 'disabled':
			$(this).each(function () {
				if (data) {
					disable($(this));
				} else {
					enable($(this));
				}
			});
			return $(this);
		case 'value':
			$(this).each(function () {
				if(typeof data !== 'string'){
					data = hsb2hex(data);
				}
				$(this).val(data).trigger('keyup');
			});
			return $(this);
			break;
		case 'destroy':
			$(this).each(function () {
				destroy($(this));
			});
			return $(this);
		default:
			if (!o) o = {};
			$(this).each(function () {
				// Must be called on an input element
				if ($(this)[0].tagName.toLowerCase() !== 'input') return;
				// If a trigger is present, the control was already created
				if ($(this).data('trigger')) return;
				// Create the control
				create($(this), o, data);
			});
			return $(this);
		}
	};

})(jQuery);


/*
* FancyBox - jQuery Plugin
* Simple and fancy lightbox alternative
*
* Examples and documentation at: http://fancybox.net
* 
* Copyright (c) 2008 - 2010 Janis Skarnelis
* That said, it is hardly a one-person project. Many people have submitted bugs, code, and offered their advice freely. Their support is greatly appreciated.
* 
* Version: 1.3.4 (11/11/2010)
* Requires: jQuery v1.3+
*
* Dual licensed under the MIT and GPL licenses:
*   http://www.opensource.org/licenses/mit-license.php
*   http://www.gnu.org/licenses/gpl.html
*/

;(function(b){var m,t,u,f,D,j,E,n,z,A,q=0,e={},o=[],p=0,d={},l=[],G=null,v=new Image,J=/\.(jpg|gif|png|bmp|jpeg)(.*)?$/i,W=/[^\.]\.(swf)\s*$/i,K,L=1,y=0,s="",r,i,h=false,B=b.extend(b("<div/>")[0],{prop:0}),M=b.browser.msie&&b.browser.version<7&&!window.XMLHttpRequest,N=function(){t.hide();v.onerror=v.onload=null;G&&G.abort();m.empty()},O=function(){if(false===e.onError(o,q,e)){t.hide();h=false}else{e.titleShow=false;e.width="auto";e.height="auto";m.html('<p id="fancybox-error">The requested content cannot be loaded.<br />Please try again later.</p>');
F()}},I=function(){var a=o[q],c,g,k,C,P,w;N();e=b.extend({},b.fn.fancybox.defaults,typeof b(a).data("fancybox")=="undefined"?e:b(a).data("fancybox"));w=e.onStart(o,q,e);if(w===false)h=false;else{if(typeof w=="object")e=b.extend(e,w);k=e.title||(a.nodeName?b(a).attr("title"):a.title)||"";if(a.nodeName&&!e.orig)e.orig=b(a).children("img:first").length?b(a).children("img:first"):b(a);if(k===""&&e.orig&&e.titleFromAlt)k=e.orig.attr("alt");c=e.href||(a.nodeName?b(a).attr("href"):a.href)||null;if(/^(?:javascript)/i.test(c)||
c=="#")c=null;if(e.type){g=e.type;if(!c)c=e.content}else if(e.content)g="html";else if(c)g=c.match(J)?"image":c.match(W)?"swf":b(a).hasClass("iframe")?"iframe":c.indexOf("#")===0?"inline":"ajax";if(g){if(g=="inline"){a=c.substr(c.indexOf("#"));g=b(a).length>0?"inline":"ajax"}e.type=g;e.href=c;e.title=k;if(e.autoDimensions)if(e.type=="html"||e.type=="inline"||e.type=="ajax"){e.width="auto";e.height="auto"}else e.autoDimensions=false;if(e.modal){e.overlayShow=true;e.hideOnOverlayClick=false;e.hideOnContentClick=
false;e.enableEscapeButton=false;e.showCloseButton=false}e.padding=parseInt(e.padding,10);e.margin=parseInt(e.margin,10);m.css("padding",e.padding+e.margin);b(".fancybox-inline-tmp").unbind("fancybox-cancel").bind("fancybox-change",function(){b(this).replaceWith(j.children())});switch(g){case "html":m.html(e.content);F();break;case "inline":if(b(a).parent().is("#fancybox-content")===true){h=false;break}b('<div class="fancybox-inline-tmp" />').hide().insertBefore(b(a)).bind("fancybox-cleanup",function(){b(this).replaceWith(j.children())}).bind("fancybox-cancel",
function(){b(this).replaceWith(m.children())});b(a).appendTo(m);F();break;case "image":h=false;b.fancybox.showActivity();v=new Image;v.onerror=function(){O()};v.onload=function(){h=true;v.onerror=v.onload=null;e.width=v.width;e.height=v.height;b("<img />").attr({id:"fancybox-img",src:v.src,alt:e.title}).appendTo(m);Q()};v.src=c;break;case "swf":e.scrolling="no";C='<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="'+e.width+'" height="'+e.height+'"><param name="movie" value="'+c+
'"></param>';P="";b.each(e.swf,function(x,H){C+='<param name="'+x+'" value="'+H+'"></param>';P+=" "+x+'="'+H+'"'});C+='<embed src="'+c+'" type="application/x-shockwave-flash" width="'+e.width+'" height="'+e.height+'"'+P+"></embed></object>";m.html(C);F();break;case "ajax":h=false;b.fancybox.showActivity();e.ajax.win=e.ajax.success;G=b.ajax(b.extend({},e.ajax,{url:c,data:e.ajax.data||{},error:function(x){x.status>0&&O()},success:function(x,H,R){if((typeof R=="object"?R:G).status==200){if(typeof e.ajax.win==
"function"){w=e.ajax.win(c,x,H,R);if(w===false){t.hide();return}else if(typeof w=="string"||typeof w=="object")x=w}m.html(x);F()}}}));break;case "iframe":Q()}}else O()}},F=function(){var a=e.width,c=e.height;a=a.toString().indexOf("%")>-1?parseInt((b(window).width()-e.margin*2)*parseFloat(a)/100,10)+"px":a=="auto"?"auto":a+"px";c=c.toString().indexOf("%")>-1?parseInt((b(window).height()-e.margin*2)*parseFloat(c)/100,10)+"px":c=="auto"?"auto":c+"px";m.wrapInner('<div style="width:'+a+";height:"+c+
";overflow: "+(e.scrolling=="auto"?"auto":e.scrolling=="yes"?"scroll":"hidden")+';position:relative;"></div>');e.width=m.width();e.height=m.height();Q()},Q=function(){var a,c;t.hide();if(f.is(":visible")&&false===d.onCleanup(l,p,d)){b.event.trigger("fancybox-cancel");h=false}else{h=true;b(j.add(u)).unbind();b(window).unbind("resize.fb scroll.fb");b(document).unbind("keydown.fb");f.is(":visible")&&d.titlePosition!=="outside"&&f.css("height",f.height());l=o;p=q;d=e;if(d.overlayShow){u.css({"background-color":d.overlayColor,
opacity:d.overlayOpacity,cursor:d.hideOnOverlayClick?"pointer":"auto",height:b(document).height()});if(!u.is(":visible")){M&&b("select:not(#fancybox-tmp select)").filter(function(){return this.style.visibility!=="hidden"}).css({visibility:"hidden"}).one("fancybox-cleanup",function(){this.style.visibility="inherit"});u.show()}}else u.hide();i=X();s=d.title||"";y=0;n.empty().removeAttr("style").removeClass();if(d.titleShow!==false){if(b.isFunction(d.titleFormat))a=d.titleFormat(s,l,p,d);else a=s&&s.length?
d.titlePosition=="float"?'<table id="fancybox-title-float-wrap" cellpadding="0" cellspacing="0"><tr><td id="fancybox-title-float-left"></td><td id="fancybox-title-float-main">'+s+'</td><td id="fancybox-title-float-right"></td></tr></table>':'<div id="fancybox-title-'+d.titlePosition+'">'+s+"</div>":false;s=a;if(!(!s||s==="")){n.addClass("fancybox-title-"+d.titlePosition).html(s).appendTo("body").show();switch(d.titlePosition){case "inside":n.css({width:i.width-d.padding*2,marginLeft:d.padding,marginRight:d.padding});
y=n.outerHeight(true);n.appendTo(D);i.height+=y;break;case "over":n.css({marginLeft:d.padding,width:i.width-d.padding*2,bottom:d.padding}).appendTo(D);break;case "float":n.css("left",parseInt((n.width()-i.width-40)/2,10)*-1).appendTo(f);break;default:n.css({width:i.width-d.padding*2,paddingLeft:d.padding,paddingRight:d.padding}).appendTo(f)}}}n.hide();if(f.is(":visible")){b(E.add(z).add(A)).hide();a=f.position();r={top:a.top,left:a.left,width:f.width(),height:f.height()};c=r.width==i.width&&r.height==
i.height;j.fadeTo(d.changeFade,0.3,function(){var g=function(){j.html(m.contents()).fadeTo(d.changeFade,1,S)};b.event.trigger("fancybox-change");j.empty().removeAttr("filter").css({"border-width":d.padding,width:i.width-d.padding*2,height:e.autoDimensions?"auto":i.height-y-d.padding*2});if(c)g();else{B.prop=0;b(B).animate({prop:1},{duration:d.changeSpeed,easing:d.easingChange,step:T,complete:g})}})}else{f.removeAttr("style");j.css("border-width",d.padding);if(d.transitionIn=="elastic"){r=V();j.html(m.contents());
f.show();if(d.opacity)i.opacity=0;B.prop=0;b(B).animate({prop:1},{duration:d.speedIn,easing:d.easingIn,step:T,complete:S})}else{d.titlePosition=="inside"&&y>0&&n.show();j.css({width:i.width-d.padding*2,height:e.autoDimensions?"auto":i.height-y-d.padding*2}).html(m.contents());f.css(i).fadeIn(d.transitionIn=="none"?0:d.speedIn,S)}}}},Y=function(){if(d.enableEscapeButton||d.enableKeyboardNav)b(document).bind("keydown.fb",function(a){if(a.keyCode==27&&d.enableEscapeButton){a.preventDefault();b.fancybox.close()}else if((a.keyCode==
37||a.keyCode==39)&&d.enableKeyboardNav&&a.target.tagName!=="INPUT"&&a.target.tagName!=="TEXTAREA"&&a.target.tagName!=="SELECT"){a.preventDefault();b.fancybox[a.keyCode==37?"prev":"next"]()}});if(d.showNavArrows){if(d.cyclic&&l.length>1||p!==0)z.show();if(d.cyclic&&l.length>1||p!=l.length-1)A.show()}else{z.hide();A.hide()}},S=function(){if(!b.support.opacity){j.get(0).style.removeAttribute("filter");f.get(0).style.removeAttribute("filter")}e.autoDimensions&&j.css("height","auto");f.css("height","auto");
s&&s.length&&n.show();d.showCloseButton&&E.show();Y();d.hideOnContentClick&&j.bind("click",b.fancybox.close);d.hideOnOverlayClick&&u.bind("click",b.fancybox.close);b(window).bind("resize.fb",b.fancybox.resize);d.centerOnScroll&&b(window).bind("scroll.fb",b.fancybox.center);if(d.type=="iframe")b('<iframe id="fancybox-frame" name="fancybox-frame'+(new Date).getTime()+'" frameborder="0" hspace="0" '+(b.browser.msie?'allowtransparency="true""':"")+' scrolling="'+e.scrolling+'" src="'+d.href+'"></iframe>').appendTo(j);
f.show();h=false;b.fancybox.center();d.onComplete(l,p,d);var a,c;if(l.length-1>p){a=l[p+1].href;if(typeof a!=="undefined"&&a.match(J)){c=new Image;c.src=a}}if(p>0){a=l[p-1].href;if(typeof a!=="undefined"&&a.match(J)){c=new Image;c.src=a}}},T=function(a){var c={width:parseInt(r.width+(i.width-r.width)*a,10),height:parseInt(r.height+(i.height-r.height)*a,10),top:parseInt(r.top+(i.top-r.top)*a,10),left:parseInt(r.left+(i.left-r.left)*a,10)};if(typeof i.opacity!=="undefined")c.opacity=a<0.5?0.5:a;f.css(c);
j.css({width:c.width-d.padding*2,height:c.height-y*a-d.padding*2})},U=function(){return[b(window).width()-d.margin*2,b(window).height()-d.margin*2,b(document).scrollLeft()+d.margin,b(document).scrollTop()+d.margin]},X=function(){var a=U(),c={},g=d.autoScale,k=d.padding*2;c.width=d.width.toString().indexOf("%")>-1?parseInt(a[0]*parseFloat(d.width)/100,10):d.width+k;c.height=d.height.toString().indexOf("%")>-1?parseInt(a[1]*parseFloat(d.height)/100,10):d.height+k;if(g&&(c.width>a[0]||c.height>a[1]))if(e.type==
"image"||e.type=="swf"){g=d.width/d.height;if(c.width>a[0]){c.width=a[0];c.height=parseInt((c.width-k)/g+k,10)}if(c.height>a[1]){c.height=a[1];c.width=parseInt((c.height-k)*g+k,10)}}else{c.width=Math.min(c.width,a[0]);c.height=Math.min(c.height,a[1])}c.top=parseInt(Math.max(a[3]-20,a[3]+(a[1]-c.height-40)*0.5),10);c.left=parseInt(Math.max(a[2]-20,a[2]+(a[0]-c.width-40)*0.5),10);return c},V=function(){var a=e.orig?b(e.orig):false,c={};if(a&&a.length){c=a.offset();c.top+=parseInt(a.css("paddingTop"),
10)||0;c.left+=parseInt(a.css("paddingLeft"),10)||0;c.top+=parseInt(a.css("border-top-width"),10)||0;c.left+=parseInt(a.css("border-left-width"),10)||0;c.width=a.width();c.height=a.height();c={width:c.width+d.padding*2,height:c.height+d.padding*2,top:c.top-d.padding-20,left:c.left-d.padding-20}}else{a=U();c={width:d.padding*2,height:d.padding*2,top:parseInt(a[3]+a[1]*0.5,10),left:parseInt(a[2]+a[0]*0.5,10)}}return c},Z=function(){if(t.is(":visible")){b("div",t).css("top",L*-40+"px");L=(L+1)%12}else clearInterval(K)};
b.fn.fancybox=function(a){if(!b(this).length)return this;b(this).data("fancybox",b.extend({},a,b.metadata?b(this).metadata():{})).unbind("click.fb").bind("click.fb",function(c){c.preventDefault();if(!h){h=true;b(this).blur();o=[];q=0;c=b(this).attr("rel")||"";if(!c||c==""||c==="nofollow")o.push(this);else{o=b("a[rel="+c+"], area[rel="+c+"]");q=o.index(this)}I()}});return this};b.fancybox=function(a,c){var g;if(!h){h=true;g=typeof c!=="undefined"?c:{};o=[];q=parseInt(g.index,10)||0;if(b.isArray(a)){for(var k=
0,C=a.length;k<C;k++)if(typeof a[k]=="object")b(a[k]).data("fancybox",b.extend({},g,a[k]));else a[k]=b({}).data("fancybox",b.extend({content:a[k]},g));o=jQuery.merge(o,a)}else{if(typeof a=="object")b(a).data("fancybox",b.extend({},g,a));else a=b({}).data("fancybox",b.extend({content:a},g));o.push(a)}if(q>o.length||q<0)q=0;I()}};b.fancybox.showActivity=function(){clearInterval(K);t.show();K=setInterval(Z,66)};b.fancybox.hideActivity=function(){t.hide()};b.fancybox.next=function(){return b.fancybox.pos(p+
1)};b.fancybox.prev=function(){return b.fancybox.pos(p-1)};b.fancybox.pos=function(a){if(!h){a=parseInt(a);o=l;if(a>-1&&a<l.length){q=a;I()}else if(d.cyclic&&l.length>1){q=a>=l.length?0:l.length-1;I()}}};b.fancybox.cancel=function(){if(!h){h=true;b.event.trigger("fancybox-cancel");N();e.onCancel(o,q,e);h=false}};b.fancybox.close=function(){function a(){u.fadeOut("fast");n.empty().hide();f.hide();b.event.trigger("fancybox-cleanup");j.empty();d.onClosed(l,p,d);l=e=[];p=q=0;d=e={};h=false}if(!(h||f.is(":hidden"))){h=
true;if(d&&false===d.onCleanup(l,p,d))h=false;else{N();b(E.add(z).add(A)).hide();b(j.add(u)).unbind();b(window).unbind("resize.fb scroll.fb");b(document).unbind("keydown.fb");j.find("iframe").attr("src",M&&/^https/i.test(window.location.href||"")?"javascript:void(false)":"about:blank");d.titlePosition!=="inside"&&n.empty();f.stop();if(d.transitionOut=="elastic"){r=V();var c=f.position();i={top:c.top,left:c.left,width:f.width(),height:f.height()};if(d.opacity)i.opacity=1;n.empty().hide();B.prop=1;
b(B).animate({prop:0},{duration:d.speedOut,easing:d.easingOut,step:T,complete:a})}else f.fadeOut(d.transitionOut=="none"?0:d.speedOut,a)}}};b.fancybox.resize=function(){u.is(":visible")&&u.css("height",b(document).height());b.fancybox.center(true)};b.fancybox.center=function(a){var c,g;if(!h){g=a===true?1:0;c=U();!g&&(f.width()>c[0]||f.height()>c[1])||f.stop().animate({top:parseInt(Math.max(c[3]-20,c[3]+(c[1]-j.height()-40)*0.5-d.padding)),left:parseInt(Math.max(c[2]-20,c[2]+(c[0]-j.width()-40)*0.5-
d.padding))},typeof a=="number"?a:200)}};b.fancybox.init=function(){if(!b("#fancybox-wrap").length){b("body").append(m=b('<div id="fancybox-tmp"></div>'),t=b('<div id="fancybox-loading"><div></div></div>'),u=b('<div id="fancybox-overlay"></div>'),f=b('<div id="fancybox-wrap"></div>'));D=b('<div id="fancybox-outer"></div>').append('<div class="fancybox-bg" id="fancybox-bg-n"></div><div class="fancybox-bg" id="fancybox-bg-ne"></div><div class="fancybox-bg" id="fancybox-bg-e"></div><div class="fancybox-bg" id="fancybox-bg-se"></div><div class="fancybox-bg" id="fancybox-bg-s"></div><div class="fancybox-bg" id="fancybox-bg-sw"></div><div class="fancybox-bg" id="fancybox-bg-w"></div><div class="fancybox-bg" id="fancybox-bg-nw"></div>').appendTo(f);
D.append(j=b('<div id="fancybox-content"></div>'),E=b('<a id="fancybox-close"></a>'),n=b('<div id="fancybox-title"></div>'),z=b('<a href="javascript:;" id="fancybox-left"><span class="fancy-ico" id="fancybox-left-ico"></span></a>'),A=b('<a href="javascript:;" id="fancybox-right"><span class="fancy-ico" id="fancybox-right-ico"></span></a>'));E.click(b.fancybox.close);t.click(b.fancybox.cancel);z.click(function(a){a.preventDefault();b.fancybox.prev()});A.click(function(a){a.preventDefault();b.fancybox.next()});
b.fn.mousewheel&&f.bind("mousewheel.fb",function(a,c){if(h)a.preventDefault();else if(b(a.target).get(0).clientHeight==0||b(a.target).get(0).scrollHeight===b(a.target).get(0).clientHeight){a.preventDefault();b.fancybox[c>0?"prev":"next"]()}});b.support.opacity||f.addClass("fancybox-ie");if(M){t.addClass("fancybox-ie6");f.addClass("fancybox-ie6");b('<iframe id="fancybox-hide-sel-frame" src="'+(/^https/i.test(window.location.href||"")?"javascript:void(false)":"about:blank")+'" scrolling="no" border="0" frameborder="0" tabindex="-1"></iframe>').prependTo(D)}}};
b.fn.fancybox.defaults={padding:10,margin:40,opacity:false,modal:false,cyclic:false,scrolling:"auto",width:560,height:340,autoScale:true,autoDimensions:true,centerOnScroll:false,ajax:{},swf:{wmode:"transparent"},hideOnOverlayClick:true,hideOnContentClick:false,overlayShow:true,overlayOpacity:0.7,overlayColor:"#777",titleShow:true,titlePosition:"float",titleFormat:null,titleFromAlt:false,transitionIn:"fade",transitionOut:"fade",speedIn:300,speedOut:300,changeSpeed:300,changeFade:"fast",easingIn:"swing",
easingOut:"swing",showCloseButton:true,showNavArrows:true,enableEscapeButton:true,enableKeyboardNav:true,onStart:function(){},onCancel:function(){},onComplete:function(){},onCleanup:function(){},onClosed:function(){},onError:function(){}};b(document).ready(function(){b.fancybox.init()})})(jQuery);	
