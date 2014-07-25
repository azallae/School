(deftemplate h2wRatio
	0 15 ratio
	((thin (9 0) (10 1))
	 (medium (6 0) (8 1) (10 0))
	 (thick (6 1) (7 0))))

(deftemplate decor
	0 11 decked
	((plain (3 1) (4 0))
	 (swirls (3 0) (5 1) (7 0))
	 (leaves (6 0) (7 1))))

(deftemplate column
	0 18 type
	((tuscan (2 1) (4 0))
	 (ionic	(3 1) (5 0))
	 (doric	(4 0) (6 1) (8 0))
	 (corinthian (6 0) (8 1) (10 0))
	 (unknown	(14 1) (16 0))))
	 
(defrule Get_Observations
	?i <- (initial-fact)
	=>
	(printout t "What is the column's height to width ratio? ")	
	(bind ?response (read)) 
	(assert (h2w ?response)) 
	(printout t "On a scale from 1 to 10, with 10 being super decorated, how decorated are the column capitals? ") 
	(bind ?response (read)) 
	(assert (dec ?response))
	(retract ?i))
	
(defrule fuzzify
	(h2w ?x)
	(dec ?y)
	=>
	(assert (h2wRatio  (?x 0) (?x 1) (?x 0)))
	(assert (decor (?y 0) (?y 1) (?y 0))))
	
(defrule defuzzify1 
	(declare (salience -1)) 
	?f <- (column ?) 
	=> 
	(bind ?t (moment-defuzzify ?f)) 
	(printout t "number: " ?t crlf)
	(if (< ?t 3)
	then
	(printout t "The column is a tuscan column." crlf)
	else
	(if (< ?t 4)
	then
	(printout t "It's an ionic column." crlf)
	else
	(if (< ?t 7)
	then
	(printout t "It's a doric column." crlf)
	else
	(if (< ?t 9) then
	(printout t "It's a corinthian column." crlf)
	else (printout t "The column is unknown." crlf))))))

(defrule t
	(h2wRatio thin)
	(decor plain)
	=>
	(assert (column tuscan)))
	
(defrule i
	(h2wRatio medium)
	(decor plain)
	=>
	(assert (column ionic)))
	
(defrule i2
	(h2wRatio thick)
	(decor plain)
	=>
	(assert (column ionic)))

(defrule d
	(h2wRatio thin)
	(decor swirls)
	=>
	(assert (column doric)))
	
(defrule d2
	(h2wRatio medium)
	(decor swirls)
	=>
	(assert (column doric)))
	
(defrule d2
	(h2wRatio thick)
	(decor swirls)
	=>
	(assert (column doric)))
	
(defrule c
	(h2wRatio thin)
	(decor leaves)
	=>
	(assert (column corinthian)))
	
(defrule c2
	(h2wRatio medium)
	(decor leaves)
	=>
	(assert (column corinthian)))
	
(defrule c3
	(h2wRatio thick)
	(decor leaves)
	=>
	(assert (column corinthian)))
	