function showIt(id){
	var currentStyle = document.getElementById(id).style.display;
	if(currentStyle == 'block')
	document.getElementById(id).style.display = 'none'
	else
	document.getElementById(id).style.display = 'block';
}

/*
Cufon.replace('#welcome .gb', {
	fontFamily:"Garamond Pro",
	separate:"none"
});

Cufon.replace('.column h2', {
  fontFamily: "Trajan Pro",
	separate:"none",
	letterSpacing:"-.1px"
});

Cufon.replace('.title h2',{
	fontFamily: "Trajan Pro",
	fontWeight:"900",
	separate:"none"
});
*/
var slideshowData, current, slideshow;
$(document).ready(function () {
  var nav = navigator.appVersion;
  
  /* Add some body classes to make it easier to style for specificness */
  if($.browser.mozilla){
    $('body').addClass('firefox');

    var version = $.browser.version.substring(0,3), majorVersion;
  	if(version == '1.8') {
  		majorVersion = 2;
  	} else if (version == '1.9') {
  		majorVersion = 3;
  	}

  	$('body').addClass('firefox'+ majorVersion);
  }
  
  if(nav.indexOf("Win") != -1) {
    $('body').addClass('windows');
  }
  
  if(nav.indexOf("Mac") != -1) {
    $('body').addClass('mac');
  }

  if($.browser.safari){
  	$('body').addClass('safari');
  }
  
  // Preloads images
  if(typeof slideshowData != 'undefined' && slideshowData.length > 0) {
    $(slideshowData).each(function (i, data) {
      $.preloadImages(data['large']);
    });
  }
  
  // Toggles the 2 columns on the events
  $('.event_toggle a').live('click', function (e) {
    e.preventDefault();
    var item = $('.event_toggle a.active');
    var target = $(this).text().toLowerCase();
    var current = item.text().toLowerCase();
    if (!($(this).hasClass('active'))) {
    	$(this).addClass('active');
	    item.removeClass('active');
    
	    $('.events .'+ target).show();
    	$('.events .'+ current).hide();
		}
  });
  
  // Adds IE 6 support for rollovers
  $('.quick_links a').live('hover', function () {
    $('.quick_links ul').show();
  }, function () {
    $('.quick_links ul').hide();
  });
  
  // When a user clicks the thumbnail
  $('.thumbs a:not(#current_slide a)').live('click', function (e) {
    e.preventDefault();

    for (var i=0; i < slideshowData.length; i++) {
    	if ($(this).find('img').attr('src') == slideshowData[i].thumb) {
    		current = i;
    		break;
    	};
    };
  
    window.pause();
    setCurrentSlide(current);
		
  });
  
    $('#gallery .pause').live('click', function(e) {
    e.preventDefault();
    if ($(this).hasClass('paused')) {
      
      window.play();
      
      $(this).removeClass('paused');
    } else {
        
     window.pause();
     $(this).addClass('paused'); 
    }
  });

// Click the right button 

  $('#gallery .right').live('click', function(e) {
    e.preventDefault();
    window.pause();
    nextSlide();
  });

	var nextSlide = function() {
		if((current+1) == slideshowData.length) {
      current = 0;
    } else {
      current++;
    }
   
		setCurrentSlide(current);
	};
	
//  Click the left button

  $('#gallery .left').live('click', function(e) {
 
    e.preventDefault();
    if((current-1) == 0) {
      current = slideshowData.length;
    } else {
      current--;
    }
    window.pause();
    setCurrentSlide(current);
  });
  

  var updateSlots = function (items) {
    if(typeof items != 'object') return;
    var len = items.length;
    for(var b = 0; b < len; b++) {
      updateSlot(b, items[b]);
    }
  }
  var updateSlot = function (position, index) {
		var slot = slideshowData[index];
		
    if(position == 2) {
      var img = $('#picture img');
  	  var el = $('#current_slide');
  	  
			//
			var i1=$('#bgpic');
			var i2=$('#fgpic');
			i1.attr('src', slot['image']);
			i2.fadeOut(750, function(){ 
				i2.attr('src',slot['image']);
				i2.attr('alt',slot['alt']);
				i2.attr('title',slot['title']);
				i2.fadeIn(10);
			});

			
	   
  	  
			
      el.animate({
        opacity: 0,
        queue: false
      }, 250, 'swing', function () {
  		  el.find('h2').html("<a href='"+ slot['link'] +"'>"+ slot['title'] +'</a>');
  		  el.find('p').html(slot['description']);
  		  el.animate({
  		    opacity: 1,
        queue: false
  		  }, 250);
      });
    }
    //update the slot's image and alt
    $('.thumbs a:not(.thumbs div a) img').eq(position).attr('src', slot.thumb).attr('alt', slot.title);
  };
  
  // Add array keys for this
  var setCurrentSlide = function(index) {
    var n = function(x) {
	return (x + slideshowData.length) % slideshowData.length;
    }
    updateSlots([n(index-2), n(index-1), n(index), n(index+1)]);

	
  }
// Quicklinks Setup
  $('.quick_links').show();
	$('.quick_links > a').bind('click', function(e) {
		// uncomment below to force users to click
			e.preventDefault();
			$('ul', $(this).parent()).toggle();
			//try {console.log($(this).parent());}catch(e){}	
			var x = $(this).find('span');
			if (x.text() == "Showing") {
				x.text("Hiding");
			} else {
				x.text("Showing");
			}
		}).prepend('<span class="hidden">Click to show </span>');
	
	$('.quick_links ul').bind('mouseleave',function() {
		$(this).hide();
		$('.quick_links > a').find('span').text("Hiding");
	});
	
    //$('.event_toggle a:not(.active)').click();
    //$('.event_toggle a:not(.active)').click();
    
    $('.academic').hide();

  $('#news_bar a').attr('href', '#news_anchor').live('click', function (e) {
    e.preventDefault();
    var current = $(this).parent().prevAll().size();
    $('div[id^=news_item_]').hide();
    $('#news_item_'+ current).show();
    $('#news_bar .active').removeClass('active').find('span').remove();
    $(this).addClass('active').append('<span class="hidden"> (Selected)</span>');
		$('#news_anchor').focus();
  });
  
  $('#campus_bar a').attr('href', '#talk_anchor').live('click', function (e) {
    e.preventDefault();
    var current = $(this).parent().prevAll().size();
    $('div[id^=campus_bar_item_]').hide();
    $('#campus_bar_item_'+ current).show();
    $('#campus_bar .active').removeClass('active').find('span').remove();
    $(this).addClass('active').append('<span class="hidden"> (Selected)</span>');
  });
  
  $('.page_options dd a').live('click', function (e) {
    e.preventDefault();
    
    var sizes = {
      'small' : '50%',
      'medium' : '60%',
      'large' : '70%'
    };
    
    $(document.body).css({
      fontSize: sizes[$(this).attr('class')]
    });
    
  });
  
  function startTimeout() {
    window.slideshow = window.setInterval(function () {
      nextSlide();
    }, 6500);
  }

	window.pause = function() {
		window.clearTimeout(slideshow);
		$('.pause').addClass('paused');
		  $('.pause img').attr('src','portal/Monet/Images/content/play.png').attr('alt','Play');
	};
	
	window.play = function() {
		clearTimeout(slideshow);
		
		$('img', '.pause').attr('src','portal/Monet/Images/content/pause.png').attr('alt','Pause');
		nextSlide();
		startTimeout();
	};
  
  /* Initialize the homepage gallery */
  current = 2;
  startTimeout();
});

(function($) {
  var cache = [];
  $.preloadImages = function() {
    var args_len = arguments.length;
    for (var i = args_len; i--;) {
      var cacheImage = document.createElement('img');
      cacheImage.src = arguments[i];
      cache.push(cacheImage);
    }
  }
})(jQuery);

// Lab Cameras
var offPic = new Image;
offPic.src = "/pics/camera.jpg";

var rvr1013Pic = new Image;
rvr1013Pic.src = "/pics/rvr1013still.jpg";

var sc1234Pic = new Image;
sc1234Pic.src = "/pics/sc1234still.jpg";

var rvr2003Pic = new Image;
rvr2003Pic.src = "/pics/rvr2003still.jpg";

var rvr2005Pic = new Image;
rvr2005Pic.src = "/pics/rvr2005still.jpg";

var rvr2011Pic = new Image;
rvr2011Pic.src = "/pics/rvr2011still.jpg";

var rvr5029Pic = new Image;
rvr5029Pic.src = "/pics/rvr5029still.jpg";

var rvr3001Pic = new Image;
rvr3001Pic.src = "/pics/rvr3001still.jpg";

var rvr3003Pic = new Image;
rvr3003Pic.src = "/pics/rvr3003still.jpg";

var rvr3005Pic = new Image;
rvr3005Pic.src = "/pics/rvr3005still.jpg";

var rvr3009Pic = new Image;
rvr3009Pic.src = "/pics/rvr3009still.jpg";

var sc1119Pic = new Image;
sc1119Pic.src = "/pics/sc1119still.jpg";

var sc1208Pic = new Image;
sc1208Pic.src = "/pics/sc1208still.jpg";

var sc1218Pic = new Image;
sc1218Pic.src = "/pics/sc1218still.jpg";

var airc1014Pic = new Image;
airc1014Pic.src = "/pics/airc1014still.jpg";

var airc1015Pic = new Image;
airc1015Pic.src = "/pics/airc1015still.jpg";

function changeLinkHref(id,newHref)
{
	document.getElementById(id).href = newHref;
}
/********************************************************************
* Function:     swapImage
*
* Description:  A regular old image swap.
*
* Parameters:   Takes two image objects, and sets the source of the
*               oldImage to the source of the newImage.
*/
function swapImage( oldImage, newImage )
{
  oldImage.src = newImage.src;
}
