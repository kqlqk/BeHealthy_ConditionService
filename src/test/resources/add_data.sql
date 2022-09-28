delete
from user_condition;
delete
from kcals_info;

insert into kcals_info (proteins, fats, carbs)
values (100, 70, 150);

insert into kcals_info (proteins, fats, carbs)
values (90, 80, 60);


insert into user_condition (user_id, kcals_info_id, gender, age, height, weight, intensity, goal)
values (1, 1, 'MALE', 19, 190, 70, 'MAX', 'LOSE');

insert into user_condition (user_id, kcals_info_id, gender, age, height, weight, intensity, goal)
values (2, 2, 'FEMALE', 25, 160, 60, 'AVG', 'MAINTAIN');