delete
from user_condition;
delete
from daily_kcals;
delete
from daily_ate_food;
delete
from user_condition_photo;
delete
from own_daily_kcals;


insert into daily_kcals (proteins, fats, carbs)
values (100, 70, 150);

insert into daily_kcals (proteins, fats, carbs)
values (90, 80, 60);


insert into user_condition (user_id, daily_kcals_id, gender, age, height, weight, intensity, goal, fat_percent)
values (1, 1, 'MALE', 19, 190, 70, 'MAX', 'LOSE', 14.3);

insert into user_condition (user_id, daily_kcals_id, gender, age, height, weight, intensity, goal, fat_percent)
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


insert into user_condition_photo(user_id, photo_path, photo_date)
values (1, '/src/test/resources/tmp_files/1--20-12-23', '2023-12-20');


insert into own_daily_kcals (proteins, fats, carbs, user_id)
values (1, 1, 1, 1);