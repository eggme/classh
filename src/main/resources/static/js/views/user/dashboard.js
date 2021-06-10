$(function (){
   $(document).on('click', '.last_course_button_template', function (){
       location.href=$(this).attr('data-url');
   });

   $(document).on('click', '.card_toolbox_button_template', function (){
       location.href=$(this).attr('data-url');
   });

   $(document).on('click', '.dashboard_board_template', function (){
      location.href=$(this).attr('data-url');
   });
});