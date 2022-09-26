delete
from user_condition;
delete
from kcals_info;

insert into kcals_info (proteins, fats, carbs)
values (100, 70, 150);

insert into kcals_info (proteins, fats, carbs)
values (90, 80, 60);


insert into user_condition (kcals_info_id, weight, intensity, user_id)
values (1, 70, 'MAX', 1);

insert into user_condition (kcals_info_id, weight, intensity, user_id)
values (2, 80, 'AVG', 2);