
;//////////////////////////////////////////////////////////////
;Initial assertions and question
;//////////////////////////////////////////////////////////////


(defrule Get_Observations
	?i <- (initial-fact)
	=>
	(assert (style greek))
	(assert (style roman))
	(assert (style romanesque))
	(assert (style gothic))
	(assert (style renaissance))
	(assert (nextQuestion1 yes))
	(retract ?i))

(defrule viewQuestion
 (nextQuestion1 yes)
 =>
 (printout t "Can you see the outside of the building? Inside? Both? [outside/inside/both] ")
 (assert (view (read))))
 
(defrule both
(view both)
=>
(assert (view inside))
(assert (view outside))
)

;//////////////////////////////////////////////////////////////
;Buttresses and Towers
;//////////////////////////////////////////////////////////////

;Note: Buttresses and Towers are bundled, because they are both exclusive to gothic and romanesque and outside


(defrule btowerQ
 (view outside)
 (or (style gothic) (style romanesque))
 (not(or (towers yes) (towers no)))
 =>
 (printout t "Does the building feature either buttresses or towers? [yes/no] ")
 (assert (towers (read))))
 
 
;//////////////////////////////////////////////////////////////
;Porch
;//////////////////////////////////////////////////////////////
 
 
 (defrule porchQ
 (view outside)
 (style greek)
 (or (towers no) (not(style gothic)) (not(style romanesque))) 
 (or (dome no) (not(style roman)) (not(style renaissance)))
 (not(or (porch yes) (porch no)))
 =>
 (printout t "Does the entrance have an open front porch area? [yes/no] ")
 (assert (porch (read))))
 
 
;//////////////////////////////////////////////////////////////
;Domes
;////////////////////////////////////////////////////////////// 
 
 
 
 
(defrule ceilingOutQ
(view outside)
(or (towers no) (not(style gothic)) (not(style romanesque))) 
(or (style roman) (style renaissance))
(not(or (dome yes) (dome no)))
 =>
 (printout t "Is the roof dome-like? [yes/no] ")
 (assert (dome (read))))
 
 
 

(defrule ceilingInQ
(view inside)
(not (dome yes))
(or (style roman) (style renaissance))
(not(or (dome yes) (dome no)))
 =>
 (printout t "Are the ceilings dome-like? [yes/no] ")
 (assert (dome (read))))

;//////////////////////////////////////////////////////////////
;Vaults
;////////////////////////////////////////////////////////////// 
 
 
 
(defrule fanVaultsQ
(view inside)
(style gothic)
(not(or (fan yes) (fan no)))
 =>
 (printout t "Do you see any fan vaults? (see picture) [yes/no] ")
 (assert (fan (read))))
 
	
(defrule ribbedVaultsQ
(view inside)
(or (style romanesque) (style gothic))
(or (fan no) (not (style gothic)))
(not(or (ribbed yes) (ribbed no)))
 =>
 (printout t "Do you see any ribbed vaults? (see picture) [yes/no] ")
 (assert (ribbed (read))))
 

(defrule groinVaultsQ
(view inside)
(or (style roman) (style romanesque) (style gothic))
(or (fan no) (not (style gothic)))
(or (ribbed no) (not (style romanesque)))
(not(or (groin yes) (groin no)))
 =>
 (printout t "Do you see any groin vaults? (see picture) [yes/no] ")
 (assert (groin (read))))
 
 
(defrule barrelVaultsQ
(view inside)
(or (style roman) (style romanesque) (style gothic) (style renaissance))
(or (fan no) (not (style gothic)))
(or (ribbed no) (not (style romanesque)))
(or (groin no) (not (style roman)))
(not(or (barrel yes) (barrel no)))
 =>
(printout t "Do you see any barrel vaults? (see picture) [yes/no] ")
(assert (barrel (read))))



;//////////////////////////////////////////////////////////////
;Tympanum
;//////////////////////////////////////////////////////////////

 
 
(defrule tympanumQ
(or (view inside) (view outside))
(or (style romanesque) (style gothic))
(not(or (tympanums yes) (tympanums no)))
 =>
 (printout t "Do you see any tympanums? (see definitions) [yes/no] ")
 (assert (tympanums (read))))


 
;//////////////////////////////////////////////////////////////
;Arches
;//////////////////////////////////////////////////////////////  
 
 
 
(defrule archesQ
(or (view inside) (view outside))
(or (style roman) (style renaissance) (style romanesque) (style gothic))
(not(or (arches yes) (arches no)))
=>
(printout t "Do you see any arches? [yes/no] ")
(assert (arches (read))))

(defrule archPointQ
(arches yes)
(style gothic)
(not(or (pointed yes) (pointed no)))
=>
(printout t "Are the arches pointed? [yes/no] ")
(assert (pointed (read))))

;//////////////////////////////////////////////////////////////
;Columns
;//////////////////////////////////////////////////////////////

(defrule columnsQ
(or (view inside) (view outside))
(not(or (columns yes) (columns no)))
=>
(printout t "Are there columns around? [yes/no] ")
(assert (columns (read))))

(defrule clusteredQ
(columns yes)
(style gothic)
(not(or (clustered yes) (clustered no)))
=>
(printout t "Are the columns made up of several smaller columns put together? [yes/no] ")
(assert (clustered (read))))

(defrule decoratedQ
(columns yes)
(or (clustered no) (not (style gothic)))
(not(or (decorated yes) (decorated no)))
=>
(printout t "Are the columns decorated on their capitals? [yes/no] ")
(assert (decorated (read))))




(defrule plainQ
(columns yes)
(decorated no)
(not(or (thick yes) (thick no)))
=>
(printout t "Would you say the columns are really thick or no? [yes/no] ")
(assert (thick (read))))

(defrule thickReduce
(thick yes)
=>
(assert (column doric)))

(defrule thinReduce
(thick no)
=>
(assert (column tuscan)))





(defrule swirliesQ
(columns yes)
(decorated yes)
(not(or (swirls yes) (swirls no)))
=>
(printout t "Do you see swirls on the capitals? [yes/no] ")
(assert (swirls (read))))

(defrule flowersQ
(columns yes)
(decorated yes)
(not(or (flowers yes) (flowers no)))
=>
(printout t "Do you see flowers and/or leaves on the capitals? [yes/no] ")
(assert (flowers (read))))

(defrule sfReduce
(flowers yes)
(swirls yes)
=>
(assert (column composite)))

(defrule swirlReduce
(flowers no)
(swirls yes)
=>
(assert (column ionic)))


;//////////////////////////////////////////////////////////////
;Negation 
;//////////////////////////////////////////////////////////////



(defrule nGreek
?style <- (style greek)
(or (column composite) (column tuscan) (clustered yes) (arches yes) (dome yes) (towers yes) (fan yes) (ribbed yes) (groin yes) (barrel yes) (tympanums yes))
=>
(retract ?style))

(defrule nRoman
?style <- (style roman)
(or (clustered yes) (pointed yes) (porch yes) (towers yes) (ribbed yes) (fan yes) (tympanums yes))
=>
(retract ?style))


(defrule nRomanesque
?style <- (style romanesque)
(or (column ionic) (column doric) (column tuscan) (clustered yes) (pointed yes) (porch yes) (dome yes) (fan yes)) 
=>
(retract ?style))


(defrule nGothic
?style <- (style gothic)
(or (pointed no) (porch yes) (dome yes)) ;if pointed no, then it must be a regular arch
=>
(retract ?style))



(defrule nRenaissance
?style <- (style renaissance)
(or (clustered yes) (pointed yes) (porch yes) (towers yes) (ribbed yes) (fan yes) (tympanums yes) (groin yes))
=>
(retract ?style))


;//////////////////////////////////////////////////////////////
;Goals
;//////////////////////////////////////////////////////////////



(defrule greekGoal
(declare (salience -1))
(style greek)
(and (not (style roman)) (not (style romanesque)) (not (style gothic)) (not (style renaissance)))
=> 
(printout t "The building's style is Greek" crlf))

(defrule romanGoal
(declare (salience -1))
(style roman)
(and (not (style greek)) (not (style romanesque)) (not (style gothic)) (not (style renaissance)))
=> 
(printout t "The building's style is Roman" crlf))

(defrule romanesqueGoal
(declare (salience -1))
(style romanesque)
(and (not (style roman)) (not (style greek)) (not (style gothic)) (not (style renaissance)))
=> 
(printout t "The building's style is Romanesque" crlf))

(defrule gothicGoal
(declare (salience -1))
(style gothic)
(and (not (style roman)) (not (style romanesque)) (not (style greek)) (not (style renaissance)))
=> 
(printout t "The building's style is Gothic" crlf))

(defrule reinassanceGoal
(declare (salience -1))
(style reinassance)
(and (not (style roman)) (not (style romanesque)) (not (style gothic)) (not (style greek)))
=> 
(printout t "The building's style is Renaissance" crlf))


(defrule unknownGoal
(declare (salience -1))
(and (style roman) (style romanesque) (style gothic) (style greek) (style renaissance))
=>
(printout t "Unable to make a determination based on current information, all styles are valid" crlf))


(defrule greekPossible
(declare (salience -100))
(style greek)
(or (style roman) (style romanesque) (style gothic) (style renaissance))
(not(gree done))
=> 
(assert(gree done)) ;Flag so this rule doesn't fire twice with 3+ matches
(printout t "Possible Style: Greek" crlf))




(defrule romanPossible
(declare (salience -100))
(style roman)
(or (style greek) (style romanesque) (style gothic) (style renaissance))
(not(roma done))
=> 
(assert(roma done))
(printout t "Possible Style: Roman" crlf))




(defrule romanesquePossible
(declare (salience -100))
(style romanesque)
(or (style roman) (style greek) (style gothic) (style renaissance))
(not (esque done))
=> 
(assert(esque done))
(printout t "Possible Style: Romanesque" crlf))




(defrule gothicPossible
(declare (salience -100))
(style gothic)
(or (style roman) (style romanesque) (style greek) (style renaissance))
(not (goth done))
=> 
(assert(goth done))
(printout t "Possible Style: Gothic" crlf))




(defrule renaissancePossible
(declare (salience -100))
(style renaissance)
(or (style roman) (style romanesque) (style gothic) (style greek))
(not(rein done))
=> 
(printout t "Possible Style: Renaissance" crlf)
(assert(rein done)))