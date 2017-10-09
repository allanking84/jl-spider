SELECT m.*, FORMAT(m.viewer/100000,1) AS viewpoint,FORMAT(m.rating+m.viewer/100000,1) AS pop  FROM movie m
ORDER BY pop+0 DESC LIMIT 30;
