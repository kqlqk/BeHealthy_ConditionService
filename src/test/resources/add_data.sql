delete
from user_condition;
delete
from daily_kcals;
delete
from daily_ate_food;


insert into daily_kcals (proteins, fats, carbs)
values (100, 70, 150);

insert into daily_kcals (proteins, fats, carbs)
values (90, 80, 60);


insert into user_condition (user_id, kcals_info_id, gender, age, height, weight, intensity, goal, fat_percent)
values (1, 1, 'MALE', 19, 190, 70, 'MAX', 'LOSE', 14.3);

insert into user_condition (user_id, kcals_info_id, gender, age, height, weight, intensity, goal, fat_percent)
values (2, 2, 'FEMALE', 25, 160, 60, 'AVG', 'MAINTAIN', 13.4);


insert into daily_ate_food (user_id, product_name, product_weight, product_kcals, product_proteins, product_fats,
                            product_carbs)
values (1, 'rice', 100, 344.1, 10.8, 3, 70.1);

insert into daily_ate_food (user_id, product_name, product_weight, product_kcals, product_proteins, product_fats,
                            product_carbs)
values (1, 'chicken', 100.1, 150, 30, 3, 0.2);

insert into daily_ate_food (user_id, product_name, product_weight, product_kcals, product_proteins, product_fats,
                            product_carbs)
values (2, 'rice', 100, 350, 10, 3, 70);