INSERT INTO public.signup_requests (id_request,first_name,last_name,email,phone,restaurant_name,message,status,created_at,resolved_at,resolved_by_admin_id,dni) VALUES
	 (1,'Carlos','Pérez','carlos@email.com','600123456','Restaurante Demo','Me interesa usar la aplicación','APPROVED','2026-01-28 19:15:24.29675','2026-01-28 19:23:04.152646',1,NULL),
	 (2,'Carlos','Pérez','carlos@email.com','600123456','Restaurante Demo','Me interesa usar la aplicación','REJECTED','2026-01-31 13:20:10.401273','2026-01-31 13:25:38.796715',1,NULL),
	 (3,'Eva','Diaz','eva@email.com','666555444','Restaurante SuperDemo','Me interesa usar la aplicación','APPROVED','2026-01-31 13:26:24.676956','2026-01-31 13:27:01.04923',1,NULL),
	 (5,'Jesus','Cristo','jesus@email.com','666555444','Restaurante El Gigante','Me interesa usar la aplicación','APPROVED','2026-01-31 17:31:08.809025','2026-01-31 17:42:43.885267',1,'55555555A'),
	 (4,'Marco','Polo','marco@email.com','666555444','Restaurante La Pili','Me interesa usar la aplicación','REJECTED','2026-01-31 15:13:08.899544','2026-01-31 17:43:05.617027',1,'55555555A'),
	 (6,'Jesus','Cristo','jesus@email.com','666555444','Restaurante El Gigante','Me interesa usar la aplicación','APPROVED','2026-01-31 17:31:30.918881','2026-01-31 18:42:29.125387',1,'77777777A');
