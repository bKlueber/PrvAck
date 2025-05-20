\\\\\\\\\\\\\\\\\\\///////GUIDE FOR EDITING THE GAMES .txt FILES ///////\\\\\\\\\\\\\\\\\\\

The game is currently coded in a way that the bulk of the content can be added by editing the .txt 
files found within. Right now there are six .txt files that should be edited and one that should not be touched

The five that are okay to edit are:

1. itemMaster.txt
2. containerMaster.txt
3. npcInventories.txt
4. masterNPC.txt
5. sceneBank.txt
6. Dialogue.txt

>>> DO NOT EDIT playerInventory.txt EXCEPT in the case you are loading a STARTING INVENTORY <<<

Again DO NOT EDIT playerInventory.txt unless you are changing the default player loadout. 

WHAT DOES EACH TXT DO?

1. itemMaster.txt

	- This .txt is foundational to a giant part of the back ends code, this is where every single item in
	  in the game, that is available from any source is sourced from. If an item does not exist inside of 
	  itemMaster.txt then that item will cause an error in the code. This is the filter that validates each
	  item and is the source that is pulled from to populate every other instance of said items.
	  
	- This .txt can be changed and edited by any team member, please feel free to add any weapon or armor you
	  can think of. The more the merrier here, as long as you follow the instructions below then adding items
	  to be used is honestly trivial. 
	  
	  Notice: just because an item is present here does not mean it HAS to be put into the game. 

2. containerMaster.txt

	- This .txt is where every single container (minus npcs if you count them) and its inventories can be found.
	  If you want to put any type of container in the game, you will add it here. 
	  
	  Notice: Containers can be empty, just like in other RPGs not all lootable objects need to have loot

3. npcInventories.txt

	-  This .txt is where each and every NPCs inventory will live. So I couldnt set up a shop, im sorry. I 
	   was forced to hone in on what was most important, also the items an NPC has do NOT affect their defense
	   in combat, sorry. I couldn't get it to work with making them 'equip' actual items, instead i set attributes
	   to control damage dished out and how their defense works and use formulas to pull values in the accepted
	   ranges. So then what does this do? When combat ends with the specified NPC (matched up with a key) 
	   the items that live in this .txt will display and allow the player a chance to loot them to reward the
	   player for progression.
	   
	   Notice: I cannot change how these work, sorry, we can improve them but a store/trading is out of scope. 
	   
4. masterNPC.txt

	- This .txt is where every single npc in the ENTIRE game needs to live. Whether it is a major character
	  or simple fodder they MUST EXIST HERE. This is where you will tie the specific attribute values to each 
	  instance of the npcs.
	  
	  Notice: This right now contains factions as an attribute but the code has no way to support it,
      the faction idea also needs to be cut im sorry. Just set them to null for now until i have time 
	  to go back into the code and tweak it so any references to factions is cut
	  
5. sceneBank.txt
	
	- This file defines every explorable “scene” (or area) in the game. Each line corresponds to one scene and also contains the 
	branching action options that let the player transition to another scene or trigger a world state change 
   (like entering COMBAT, LOOTING, or DIALOGUE).
	
	Notice: The scenes do not need to include combat scenes (descriptions), instead when combat starts from either player selection
	or triggered event the enumeration for the combat world state comes into play and the world state will shift out
	of the scene until combat has eneded.
	
6. Dialogue.txt

	- This .txt is where all the diaglogue choices will live for the entire game including each and every single branching
	option possible for the players to choose from. 
	
	Notice: Make sure that you include some prompt to end the conversation or the player will be stuck and the codes
	world state wont change back to exploring. 

7. playerInventory.txt

	- This .txt is where the playersInventory will be stored, this should not be edited. To reiterate, do not edit
	unless you are changing the players default loadout.
	
	Notice: This .txt will update automatically as the player interacts with the game, loots and progresses.

HOW DO I EDIT EACH TXT?

1. itemMaster.txt
	
	 >>> NOT ALL ATTRIBUTES ARE APPLICABLE TO EVERY ITEM, IF NECESSARY USE NULL OR ITS EQUIVILANT <<<
	 
	ITEMS HAVE EIGHT TOTAL ATTRIBUTES THAT MUST BE ASSIGNED IN THIS ORDER:
	
	1.1 Item Name - STRING
		- This is self explanatory, just the item name.
	
	1.2 item ID - STRING
		- This is an extremely important attribute and will be referenced in a lot of methods so make
		sure that each and every item has a unique id. Also PLEASE make some sort of like structure/pattern
		to how you assign these IDs it will make connecting everthing much easier.
		
	1.3 item Description - STRING
		- Self explanatory this just gives a description of the item. 
		
	1.4 item Value //this I just havent gotten around to removing but i will get to it, without a bartering system this is useless
	1.5 item Weight //this is also useless and needs to be removed from the code completely
	
	1.6 item Damage - INT
		- This attribute is used in combat as part of the formula that calculates total player damage to hostile npcs
		Kind of obvious but cannot be a negative value. Also need to be an integer. 
		
	1.7 item Armor - INT
		- This attribute is used in combat as part of the formula that calculates total damage the player receives
		from hostile npcs. Kind of obvious but cannot be a negative value. Also need to be an integer.
	
	1.8 item Affinity - STRING 
		- This attribute controls what value is used when calculating the possible damage from the players item.
		The back end code uses a formula mixing item affinity and stats as a factor for the total damage delt. 
	
2. containerMaster.txt
	
	There are Only two attributes tied to containers, in order they are:
	
	2.1 Container ID - STRING
		- These container IDs need to be unique and please make sure that each follows the same structure/pattern.
		
	2.2 A hashmap of items - HASHMAP
		- These items should be listed out only by their item ID and separated from the Container ID with the char "|" and each item 
		separated witha comma ",".  See .txt for example if you need clarity.
	
3. npcInventories.txt

There are Only two attributes tied to NPC inventories, in order they are:
	
	3.1 NPC ID - STRING
		- These NPCs need to be first living inside of masterNPCs and please make sure that each follows the same structure/pattern.
		
	3.2 A hashmap of items - HASHMAP
		- These items should be listed out only by their item ID and separated from the NPC ID with the char "|" and each item 
		separated witha comma ",".  See .txt for example if you need clarity.

	
4. masterNPC.txt

	masterNPC.txt has nine total attributes to fill out in order:
	
		4.1 NPC Name - STRING 
			- The name assigned to the npc.
		
		4.2 NPC ID - STRING
			- The ID assigned to the npc. Needs to be unique as it will be referenced in many methods.
		
		4.3 NPC Description - STRING
			- The description assigned to the npcs, may not be visible to player right now but can be useful for us

		4.4 NPC Health - INT
			- This is very important for combat, but make sure that this value is always always greater than 0.
		
		4.5 NPC Can Combat? - BOOLEAN
			- This is just one of the flag controls used to make sure an NPC should be in combat for t
		
		4.6 NPC Faction - STRING //we can fill this out if wanted for like our references but i cant code factions in as stated above, so imo leave NULL
		
		4.7 Base Armor - INT
			- This value is taken into account for the formula that calculates how much damage a player can do to a hositle npc
		
		4.8 Min Damage - INT
			- The minimum damage an enemy npc can do.
		
		4.9 Max Damage - INT
			- The maximum damage an enemy npc can do.
		
	Notice: The min and max damages can be used to scale the difficulty of the game as the player progresses, earlier in  game means lower values for npcs
	as they progress towards end game have the values go up to increase challenge

5. sceneBank.txt
	
	This .txt has three attributes that need to be filled out, with a "|" char seperating the scene description and the user choices. They must follow this exact order
	
		5.1 Scene ID - STRING
			- This is the unique ID that needs to be assigned to each scene, it is also what will be referenced when a scene transitions from one to
			another. For that reason I highly recommend there being well thought out logic in the naming so when the scenes are connected it can be done easily
			
		5.2  Scene Description - STRING
			- This is the actual narrative text that is displayed to the user, the purpose of this is to describe the area the player has entered
			as well as reference any relevant information from the actual physical scene (since its all text based we obviously want to be descriptive
			when it makes sense too.) You should not just decribe the area phsycially but set the mood, ex "Player steps in and can feel a humming tension spread across 
			their body, unease starts to set in...." or something like that.

		5.3 Actions - String 
			- These are the actions available to a player. There are a limited amount of actions you can choose from, these being:	
				TRANSITION TO NEW SCENE
				LOOT OBJECT
				START DIALOGUE
				START COMBAT
			
			!!!!!!!!!!!!!!!IMPORTANT!!!!!!!!!!!!!!!
			I CAN NOT STRESS THIS ENOUGH, THIS NEXT PART CONTROLS LITERALLY THE ENTIRE FLOW OF THE GAME
			
			The actions are made of two parts, a label and a trigger with an "->" between them, like so:
			
			Label->Trigger
			
			Label: The text displayed to the player, for ex "Search this box", "Enter the run down home", "Attack ed!"
			Trigger: Either the ID of the next scene or the world state (enum) youd like to call and therefore entered.
			The world states relevant are:
			
			LOOTING
			COMBAT
			DIALOGUE
			
			Example:
			
			S001|You stand at the edge of a dark forest, with a narrow path leading inside.|"Enter Forest->S002, Return to Town->T001, Search Box->LOOTING"
	
			
	Notice: The Actions field must use the exact "Label->Trigger" format without any extra spaces that could confuse the parser.

6. Dialogue.txt

	Every line within this .txt is for one dialogue interaction, and must have at minimum 7 fields but in theory could be infinitely large. They must
	also follow this order exactly
	
		6.1 NPC ID - STRING
			- Here just put the npcs ID that this interaction is with for now, it doenst do anything but I have a suspicion later it may be useful
			unforutnately interactoions with Dialogue has to be one on one for now... sorry about that
		
		6.2 – NPC Name - STRING
			- The name of the NPC speaking in this dialogue.
			In game, the dialogue is displayed as:
			<NPC Name>: <Dialogue Text>
			
		6.3 – Extra Data - STRING
			- (Optional) This field can hold additional info (or be left blank) that isn’t directly used by the dialogue system.
			I intended this to be used for us to keep track of what interaction this is with that npc so like firstInteraction, angryResponse,
			friendlyRepsonse, firstGreeting, etc.
			
		6.4 – Dialogue ID - STRING
			- A unique identifier for this dialogue entry.
			  This ID is used as a key for the branching dialogue system.
			  
		6.5 – Dialogue Text - STRING
			- The actual dialogue line spoken by the NPC.

		6.6 – Player Response Option 1 - STRING
			-The first response option that the player can select.
			
		6.7 – Next Dialogue ID for Option 1 - STRING
			- The dialogue entry to transition to when the player selects the first response.
			If the conversation should end after this option, put 0 (zero).
			
>>>>>>>>>> NEED OR WANT MORE OPTIONS FOR THE DIALOGUE? <<<<<<<<<<
	To add more dialogue choices, continue adding fields in pairs:
		
		Field 8 – Player Response Option 2
		Field 9 – Next Dialogue ID for Option 2
		Field 10 – Player Response Option 3
		Field 11 – Next Dialogue ID for Option 3, etc.



>>>>>>>>>> WANT AN IN GAME EVENT TO TRIGGER SOMETHING? <<<<<<<<<<
If you want an in game action to trigger a specific event then theres some information we will need
for example if you want an item being aquired by the player, an object being looked at closer (through an object specific scene),
a dialogue choice being made to trigger something in the story to progress then theres info we will need

- Triggering event (e.g., specific dialogue choice, looting a rare item, entering a hidden scene).
- Expected outcome (e.g., unlocking a new quest, changing an NPC’s attitude, gaining faction points).
- Any required conditions (e.g., player must have a certain stat level or previous interaction).

>>>>>>>>>> EXPLORE OBJECT WITH SCENES <<<<<<<<<<
I know i didnt list is above but you can use these scenes to also investrigate certain objects, instead of for example interacting with 
a door taking you to a new scene you can also interact with a book shelf and give the player a new "bookshelf scene" if that makes sense
where they can investigate things closer then when the user is done investigating just make one of the options go back to the source scene
