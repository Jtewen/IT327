fun worker i [] = [[i]]
  | worker i (h::t) = (i::h::t)::(map (fn l => h::l) (worker i t));

fun listify ls = foldr op @ [] ls;

fun permute [] = [nil]
  | permute (h::t) = listify (map (worker h) (permute t));
