(deftemplate funding
	20 80 thousands
	((adequate 		(50 0) (70 1))
	 (marginal		(30 0) (50 1) (70 0))
	 (inadequate	(30 1) (50 0))))
(deftemplate staffing
	10 50 persons
	((small 	(20 1) (40 0))
	 (large		(20 0) (40 1))))
(deftemplate risk
	0 10 percent
	((low		(2 1) (5 0))
	 (normal	(2 0) (5 1) (8 0))
	 (high		(5 0) (8 1))))
	 
(defrule fuzzify
	?i <- (initial-fact)
	=>
	(assert (funding  (32 0) (32 1) (32 0)))
	(assert (staffing (35 0) (35 1)))
	(retract ?i))
 
(defrule defuzzify1
	(declare (salience -1))
	?f <- (risk ?)
	=>
	(bind ?t (moment-defuzzify ?f))
	(printout t "value: "
 
(defrule LowRisk
	(funding adequate)
	=> 
	(assert(risk low)))
 
(defrule LowRisk2 
	(staffing small)
	=> 
	(assert(risk low)))
 
(defrule NormalRisk 
	(funding marginal)
	(staffing large)
	=> 
	(assert(risk normal)))
 
(defrule HighRisk 
	(funding inadequate)
	=> 
	(assert(risk high)))
